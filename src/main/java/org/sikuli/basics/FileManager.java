/*
 * Copyright 2010-2013, Sikuli.org
 * Released under the MIT License.
 *
 * modified RaiMan 2013
 */
package org.sikuli.basics;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.ServiceLoader;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class FileManager {

  //<editor-fold defaultstate="collapsed" desc="new logging concept">
  private static String me = "FileManager";
  private static String mem = "...";
  private static int lvl = 3;

  private static void log(int level, String message, Object... args) {
    Debug.logx(level, "", me + ": " + mem + ": " + message, args);
  }

  private static void log0(int level, String message, Object... args) {
    Debug.logx(level, "", me + ": " + message, args);
  }
  //</editor-fold>  
  
  static final int DOWNLOAD_BUFFER_SIZE = 153600;
  static IResourceLoader nativeLoader = null;
  private static MultiFrame _progress = null;
  private static final String EXECUTABLE = "#executable";

  /**
   * System.load() the given library module <br />
   * from standard places (folder libs or SikuliX/libs) in the following order<br />
   * 1. -Dsikuli.Home=<br /> 2. Environement SIKULIX_HOME<br />
   * 3. parent folder of sikuli-script.jar (or main jar)<br />
   * 4. folder user's home (user.home)<br/>
   * 5. current working dir or parent of current working dir<br />
   * 6. standard installation places of Sikuli
   *
   * @param libname
   * @param doLoad = true: load it here
   * @throws IOException
   */
  public static void loadLibrary(String libname) {
    String jarPath = isFatJar();
    if (jarPath != null) {
      RunSetup.popError("Terminating: The jar in use was not built with setup!\n" + jarPath);
      System.exit(1);
    }
    if (nativeLoader == null) {
      nativeLoader = getNativeLoader("basic", null);
    }
    nativeLoader.check(Settings.SIKULI_LIB);
    nativeLoader.doSomethingSpecial("loadLib", new String[]{libname});
  }

  private static int tryGetFileSize(URL url) {
    HttpURLConnection conn = null;
    try {
      conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("HEAD");
      conn.getInputStream();
      return conn.getContentLength();
    } catch (IOException e) {
      return -1;
    } finally {
      conn.disconnect();
    }
  }

  
  /**
   * download a file at the given url to a local folder
   *
   * @param url a valid url
   * @param localPath the folder where the file should go (will be created if necessary)
   * @return the absolute path to the downloaded file or null on any error
   */
  public static String downloadURL(URL url, String localPath) {
    String[] path = url.getPath().split("/");
    String filename = path[path.length - 1];
    String targetPath = null;
    int srcLength = 0;
    int srcLengthKB = -1;
    int done;
    int totalBytesRead = 0;
    File fullpath = new File(localPath);
    if (fullpath.exists()) {
      if (fullpath.isFile()) {
        log0(-1, "download: target path must be a folder:" + localPath);
        fullpath = null;
      }
    } else {
      if (!fullpath.mkdirs()) {
        log0(-1, "download: could not create target folder: " + localPath);
        fullpath = null;
      }
    }
    if (fullpath != null) {
      fullpath = new File(localPath, filename);
      targetPath = fullpath.getAbsolutePath();
      srcLength = tryGetFileSize(url);
      if (srcLength > 0) {
        srcLengthKB = (int) (srcLength / 1024);
      }
      log0(lvl, "Downloading %s having %d KB", filename, srcLengthKB);
      done = 0;
      if (_progress != null) {
        _progress.setProFile(filename);
        _progress.setProSize(srcLengthKB);
        _progress.setProDone(0);
        _progress.setVisible(true);
      }
      try {
        FileOutputStream writer = new FileOutputStream(fullpath);
        InputStream reader = url.openStream();
        byte[] buffer = new byte[DOWNLOAD_BUFFER_SIZE];
        int bytesRead = 0;
        long begin_t = (new Date()).getTime();
        long chunk = (new Date()).getTime();
        while ((bytesRead = reader.read(buffer)) > 0) {
          writer.write(buffer, 0, bytesRead);
          totalBytesRead += bytesRead;
          if (srcLength > 0) {
            done = (int) ((totalBytesRead / (double) srcLength) * 100);
          } else {
            done = (int) (totalBytesRead / 1024);
          }
          if (((new Date()).getTime() - chunk) > 1000) {
            if (_progress != null) {
              _progress.setProDone(done);
            }
            chunk = (new Date()).getTime();
          }
        }
        reader.close();
        writer.close();
        log0(lvl, "downloaded %d KB to %s", (int) (totalBytesRead / 1024), targetPath);
        log0(lvl, "download time: %d", (int) (((new Date()).getTime() - begin_t) / 1000));
      } catch (IOException ex) {
        log0(-1, "problems while downloading\n" + ex.getMessage());
        targetPath = null;
      }
      if (_progress != null) {
        if (targetPath == null) {
          _progress.setProDone(-1);
        } else {
          if (srcLength <= 0) {
            _progress.setProSize((int) (totalBytesRead / 1024));
          }
          _progress.setProDone(100);
        }
        _progress.closeAfter(3);
        _progress = null;
      }
    }
    return targetPath;
  }

  /**
   * download a file at the given url to a local folder
   *
   * @param url a string representing a valid url
   * @param localPath the folder where the file should go (will be created if necessary)
   * @return the absolute path to the downloaded file or null on any error
   */
  public static String downloadURL(String url, String localPath) {
    URL src = null;
    try {
      src = new URL(url);
    } catch (MalformedURLException ex) {
      log0(-1, "download: bad URL: " + url);
      return null;
    }
    return downloadURL(src, localPath);
  }

  public static String downloadURL(String url, String localPath, JFrame progress) {
    _progress = (MultiFrame) progress;
    return downloadURL(url, localPath);
  }

  /**
   * open the given url in the standard browser
   *
   * @param url string representing a valid url
   * @return false on error, true otherwise
   */
  public static boolean openURL(String url) {
    try {
      URL u = new URL(url);
      Desktop.getDesktop().browse(u.toURI());
    } catch (Exception ex) {
      log0(-1, "show in browser: bad URL: " + url);
      return false;
    }
    return true;
  }

  public static String unzipSKL(String fileName) {
    File file;
    try {
      file = new File(fileName);
      if (!file.exists()) {
        throw new IOException(fileName + ": No such file");
      }
      String name = file.getName();
      name = name.substring(0, name.lastIndexOf('.'));
      File tmpDir = createTempDir();
      File sikuliDir = new File(tmpDir + File.separator + name + ".sikuli");
      sikuliDir.mkdir();
      sikuliDir.deleteOnExit();
      unzip(fileName, sikuliDir.getAbsolutePath());
      return sikuliDir.getAbsolutePath();
    } catch (IOException e) {
      System.err.println(e.getMessage());
      return null;
    }
  }

  public static File createTempDir() {
    Random rand = new Random();
    int randomInt = 1 + rand.nextInt();

    File tempDir = new File(Settings.BaseTempPath + File.separator + "tmp-" + randomInt + ".sikuli");
    if (tempDir.exists() == false) {
      tempDir.mkdirs();
    }

    tempDir.deleteOnExit();

    log0(lvl, "tempdir create: %s", tempDir);

    return tempDir;
  }

  public static void deleteTempDir(String path) {
    if (!deleteFileOrFolder(path)) {
      log0(-1, "tempdir delete not possible: %s", path);
    } else {
      log0(lvl, "tempdir delete: %s", path);
    }
  }
  
  public static boolean deleteFileOrFolder(String path, fileFilter filter) {
    File entry = new File(path);
    File f;
    String[] entries;
    boolean somethingLeft = false;
    if (entry.isDirectory()) {
      entries = entry.list();
      for (int i = 0; i < entries.length; i++) {
        f = new File(entry, entries[i]);
        if (filter != null && !filter.accept(f)) {
          somethingLeft = true;
          continue;
        }
        if (f.isDirectory()) {
          if (!deleteFileOrFolder(f.getAbsolutePath())) {
            return false;
          }
        } else {
          // return file entries
          if (!f.delete()) {
            return false;
          }
        }
      }
    }
    // deletes intermediate empty directories and finally the top now empty dir
    if (!somethingLeft && entry.exists()) {
      return entry.delete();
    }
    return true;
  }
  
  public static boolean deleteFileOrFolder(String path) {
    return deleteFileOrFolder(path, null);
  }

  public static File createTempFile(String suffix) {
    return createTempFile(suffix, null);
  }

  public static File createTempFile(String suffix, String path) {
    String temp1 = "sikuli-";
    String temp2 = "." + suffix;
    File fpath = null;
    if (path != null) {
      fpath = new File(path);
    }
    try {
      File temp = File.createTempFile(temp1, temp2, fpath);
      temp.deleteOnExit();
      log0(lvl, "tempfile create: %s", temp.getAbsolutePath());
      return temp;
    } catch (IOException ex) {
      log0(-1, "createTempFile: IOException: %s", fpath + File.pathSeparator + temp1 + "12....56" + temp2);
      return null;
    }
  }

  public static String saveTmpImage(BufferedImage img) {
    return saveTmpImage(img, null);
  }

  public static String saveTmpImage(BufferedImage img, String path) {
    File tempFile;
    try {
      tempFile = createTempFile("png", path);
      if (tempFile != null) {
        ImageIO.write(img, "png", tempFile);
        return tempFile.getAbsolutePath();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static void unzip(String zip, String path)
          throws IOException, FileNotFoundException {
    final int BUF_SIZE = 2048;
    FileInputStream fis = new FileInputStream(zip);
    ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
    ZipEntry entry;
    while ((entry = zis.getNextEntry()) != null) {
      int count;
      byte data[] = new byte[BUF_SIZE];
      FileOutputStream fos = new FileOutputStream(
              new File(path, entry.getName()));
      BufferedOutputStream dest = new BufferedOutputStream(fos, BUF_SIZE);
      while ((count = zis.read(data, 0, BUF_SIZE)) != -1) {
        dest.write(data, 0, count);
      }
      dest.close();
    }
    zis.close();
  }

  public static void xcopy(String src, String dest, String current) throws IOException {
    File fSrc = new File(src);
    File fDest = new File(dest);
    if (fSrc.getAbsolutePath().equals(fDest.getAbsolutePath())) {
      return;
    }
    if (fSrc.isDirectory()) {
      if (!fDest.exists()) {
        fDest.mkdir();
      }
      String[] children = fSrc.list();
      for (String child : children) {
        if (current != null && (child.endsWith(".py") || child.endsWith(".html"))
                && child.startsWith(current + ".")) {
          log0(lvl, "xcopy: SaveAs: deleting %s", child);
          continue;
        } else if (child.endsWith("$py.class")) {
          continue;
        }
        xcopy(src + File.separator + child, dest + File.separator + child, null);
      }
    } else {
      if (fDest.isDirectory()) {
        dest += File.separator + fSrc.getName();
      }
      InputStream in = new FileInputStream(src);
      OutputStream out = new FileOutputStream(dest);
      // Copy the bits from instream to outstream
      byte[] buf = new byte[1024];
      int len;
      while ((len = in.read(buf)) > 0) {
        out.write(buf, 0, len);
      }
      in.close();
      out.close();
    }
  }

  /**
   * Copy a file *src* to the path *dest* and check if the file name conflicts. If a file with the
   * same name exists in that path, rename *src* to an alternative name.
   */
  public static File smartCopy(String src, String dest) throws IOException {
    File fSrc = new File(src);
    String newName = fSrc.getName();
    File fDest = new File(dest, newName);
    if (fSrc.equals(fDest)) {
      return fDest;
    }
    while (fDest.exists()) {
      newName = getAltFilename(newName);
      fDest = new File(dest, newName);
    }
    FileManager.xcopy(src, fDest.getAbsolutePath(), null);
    if (fDest.exists()) {
      return fDest;
    }
    return null;
  }

  public static String convertStreamToString(InputStream is) {
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    StringBuilder sb = new StringBuilder();
    String line;
    try {
      while ((line = reader.readLine()) != null) {
        sb.append(line).append("\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        is.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return sb.toString();
  }

  public static String getAltFilename(String filename) {
    int pDot = filename.lastIndexOf('.');
    int pDash = filename.lastIndexOf('-');
    int ver = 1;
    String postfix = filename.substring(pDot);
    String name;
    if (pDash >= 0) {
      name = filename.substring(0, pDash);
      ver = Integer.parseInt(filename.substring(pDash + 1, pDot));
      ver++;
    } else {
      name = filename.substring(0, pDot);
    }
    return name + "-" + ver + postfix;
  }

  public static boolean exists(String path) {
    File f = new File(path);
    return f.exists();
  }

  public static void mkdir(String path) {
    File f = new File(path);
    if (!f.exists()) {
      f.mkdirs();
    }
  }

  public static String getName(String filename) {
    File f = new File(filename);
    return f.getName();
  }

  public static String slashify(String path, Boolean isDirectory) {
    String p;
    if (path == null) {
      p = "";
    } else {
      p = path;
      if (File.separatorChar != '/') {
        p = p.replace(File.separatorChar, '/');
      }
      if (isDirectory != null) {
        if (isDirectory) {
          if (!p.endsWith("/")) {
            p = p + "/";
          }
        } else if (p.endsWith("/")) {
          p = p.substring(0, p.length() - 1);
        }
      }
    }
    if (p.contains("%")) {
      try {
        return URLDecoder.decode(p, "UTF-8");
      } catch (UnsupportedEncodingException ex) {
      }
    }
    return p;
  }

  /**
   * Retrieves the actual script file<br /> - from a folder script.sikuli<br />
   * - from a folder script (no extension) (script.sikuli is used, if exists)<br /> - from a file
   * script.skl or script.zip (after unzipping to temp)<br /> - from a jar script.jar (after
   * preparing as extension)<br />
   *
   * @param scriptName one of the above.
   * @return The File containing the actual script.
   */
  public static File getScriptFile(File scriptName, IScriptRunner runner, String[] args) {
    if (scriptName == null) {
      return null;
    }
    String script;
    String scriptType;
    File scriptFile = null;
    if (scriptName.getPath().contains("..")) {
      //TODO accept double-dot pathnames
      log0(-1, "Sorry, scriptnames with dot or double-dot path elements are not supported: %s", scriptName.getPath());
      SikuliX.terminate(0);
    }
    int pos = scriptName.getName().lastIndexOf(".");
    if (pos == -1) {
      script = scriptName.getName();
      scriptType = "sikuli";
      scriptName = new File(scriptName.getAbsolutePath() + ".sikuli");
    } else {
      script = scriptName.getName().substring(0, pos);
      scriptType = scriptName.getName().substring(pos + 1);
    }
    if (!scriptName.exists()) {
      log0(-1, "Not a valid Sikuli script: " + scriptName.getAbsolutePath());
      SikuliX.terminate(0);
    }
    if ("skl".equals(scriptType) || "zip".equals(scriptType)) {
      //TODO unzip to temp and run from there
      return null; // until ready
    }
    if ("sikuli".equals(scriptType)) {
      if (runner == null) {
        // check for script.xxx inside folder
        File[] content = scriptName.listFiles(new FileFilterScript(script + "."));
        if (content == null || content.length == 0) {
          log0(-1, "Script folder %s does not contain a script file named %s.xxx", scriptName, script);
          SikuliX.terminate(0);
        }
        for (File f : content) {
//TODO should be possible,to have more than one script type in one .sikuli
          if (f.getName().endsWith(".html")) continue;
          scriptFile = f;
          break;
        }
        scriptType = scriptFile.getName().substring(scriptFile.getName().lastIndexOf(".") + 1);
        runner = SikuliX.getScriptRunner(null, scriptType, args);
      }
      if (scriptFile == null) {
        // try with fileending
        scriptFile = (new File(scriptName, script + "." + runner.getFileEndings()[0])).getAbsoluteFile();
        if (!scriptFile.exists() || scriptFile.isDirectory()) {
          // try without fileending
          scriptFile = new File(scriptName, script);
          if (!scriptFile.exists() || scriptFile.isDirectory()) {
            log0(-1, "No runnable script found in %s", scriptFile.getAbsolutePath());
            return null;
          }
        }
      }
    }
    if ("jar".equals(scriptType)) {
      //TODO try to load and run as extension
      return null; // until ready
    }
    return scriptFile;
  }

  /**
   * Returns the directory that contains the images used by the ScriptRunner.
   *
   * @param scriptFile The file containing the script.
   * @return The directory containing the images.
   */
  public static File resolveImagePath(File scriptFile) {
    if (!scriptFile.isDirectory()) {
      return scriptFile.getParentFile();
    }
    return scriptFile;
  }

  private static String isFatJar() {
    boolean extractingFromJar = false;
    String jarPath = null;
    URL jarURL = null;
    CodeSource codeSrc = FileManager.class.getProtectionDomain().getCodeSource();
    if (codeSrc != null && codeSrc.getLocation() != null) {
      jarURL = codeSrc.getLocation();
      jarPath = jarURL.getPath();
      if (jarPath.endsWith(".jar")) {
        extractingFromJar = true;
      }
    }
    if (extractingFromJar) {
      try {
        ZipInputStream zip = new ZipInputStream(jarURL.openStream());
        ZipEntry ze;
        while ((ze = zip.getNextEntry()) != null) {
          String entryName = ze.getName();
          if (entryName.startsWith(Settings.libSourcebase)) {
            return null;
          }
        }
      } catch (IOException e) {
        return jarPath;
      }
    } else {
      return null;
    }
    return jarPath;
  }

  private static class FileFilterScript implements FilenameFilter {
    private String _check;
    public FileFilterScript(String check) {
      _check = check;
    }
    @Override
    public boolean accept(File dir, String fileName) {
      return fileName.startsWith(_check);
    }
  }

  public static IResourceLoader getNativeLoader(String name, String[] args) {
    if (nativeLoader != null) {
      return nativeLoader;
    }
    IResourceLoader nl = null;
    ServiceLoader<IResourceLoader> loader = ServiceLoader.load(IResourceLoader.class);
    Iterator<IResourceLoader> scriptRunnerIterator = loader.iterator();
    while (scriptRunnerIterator.hasNext()) {
      IResourceLoader currentRunner = scriptRunnerIterator.next();
      if ((name != null && currentRunner.getName().toLowerCase().equals(name.toLowerCase()))) {
        nl = currentRunner;
        nl.init(args);
        break;
      }
    }
    if (nl == null) {
      log0(-1, "Fatal error 121: Could not load any NativeLoader!");
      SikuliX.terminate(121);
    } else {
      nativeLoader = nl;
    }
    return nativeLoader;
  }
  
  public static String getJarParentFolder() {
    CodeSource src = FileManager.class.getProtectionDomain().getCodeSource();
    String jarParentPath = "--- not known ---";
    String RunningFromJar = "Y";
    if (src.getLocation() != null) {
      String jarPath = src.getLocation().getPath();
      if (!jarPath.endsWith(".jar")) RunningFromJar = "N";
      jarParentPath = FileManager.slashify((new File(jarPath)).getParent(), true);
    } else {
      log(-1, "Fatal Error 101: Not possible to access the jar files!");
      SikuliX.terminate(101);
    }
    return RunningFromJar + jarParentPath;
  }
  
  public static boolean writeStringToFile(String text, String path) {
    PrintStream out = null;
    try {
      out = new PrintStream(new FileOutputStream(path));
      out.print(text);
    } catch (Exception e) {
      log0(-1,"writeStringToFile: did not work: " + path + "\n" + e.getMessage());
    }
    if (out != null) {
      out.close();
      return true;
    }
    return false;
  }

  public static boolean packJar(String folderName, String jarName, String prefix) {
    jarName = FileManager.slashify(jarName, false);
    if (!jarName.endsWith(".jar")) {
      jarName += ".jar";
    }
    folderName = FileManager.slashify(folderName, true);
    if (!(new File(folderName)).isDirectory()) {
      log0(-1, "packJar: not a directory or does not exist: " + folderName);
      return false;
    }
    try {
      File dir = new File((new File(jarName)).getAbsolutePath()).getParentFile();
      if (dir != null) {
        if (!dir.exists()) {
          dir.mkdirs();
        }
      } else {
        throw new Exception("workdir is null");
      }
      log0(lvl, "packJar: %s from %s in workDir %s", jarName, folderName, dir.getAbsolutePath());
      if (!folderName.startsWith("http://") && !folderName.startsWith("https://")) {
        folderName = "file://" + (new File(folderName)).getAbsolutePath();
      }
      URL src = new URL(folderName);
      JarOutputStream jout = new JarOutputStream(new FileOutputStream(jarName));
      addToJar(jout, new File(src.getFile()), prefix);
      jout.close();
    } catch (Exception ex) {
      log0(-1, "packJar: " + ex.getMessage());
      return false;
    }
    log0(lvl, "packJar: completed");
    return true;
  }

  public static boolean buildJar(String jarName, String[] jars, String[] files, String[] prefixs, FileManager.JarFileFilter filter) {
    log0(lvl, "buildJar: " + jarName);
    try {
      JarOutputStream jout = new JarOutputStream(new FileOutputStream(jarName));
      ArrayList done = new ArrayList();
      for (int i = 0; i < jars.length; i++) {
        if (jars[i] == null) {
          continue;
        }
        log0(lvl, "buildJar: adding: " + jars[i]);
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(jars[i]));
        ZipInputStream zin = new ZipInputStream(bin);
        for (ZipEntry zipentry = zin.getNextEntry(); zipentry != null; zipentry = zin.getNextEntry()) {
          if (filter == null || filter.accept(zipentry)) {
            if (!done.contains(zipentry.getName())) {
              jout.putNextEntry(zipentry);
              if (!zipentry.isDirectory()) {
                bufferedWrite(zin, jout);
              }
              done.add(zipentry.getName());
              log0(lvl+2, "adding: " + zipentry.getName());
            }
          }
        }
        zin.close();
        bin.close();
      }
      if (files != null) {
        for (int i = 0; i < files.length; i++) {
          log0(lvl, "buildJar: adding: " + files[i]);
          addToJar(jout, new File(files[i]), prefixs[i]);
        }
      }
      jout.close();
    } catch (Exception ex) {
      log0(-1, "buildJar: " + ex.getMessage());
      return false;
    }
    log0(lvl, "buildJar: completed");
    return true;
  }

  public static boolean unpackJar(String jarName, String folderName, boolean del) {
    ZipInputStream in = null;
    BufferedOutputStream out = null;
    try {
      if (del) {
        FileManager.deleteFileOrFolder(folderName);
      }
      in = new ZipInputStream(new BufferedInputStream(new FileInputStream(jarName)));
      log0(lvl, "unpackJar: %s to %s", jarName, folderName);
      boolean isExecutable;
      int n;
      File f;
      for (ZipEntry z = in.getNextEntry(); z != null; z = in.getNextEntry()) {
        if (z.isDirectory()) {
          (new File(folderName, z.getName())).mkdirs();
        } else {
          n = z.getName().lastIndexOf(EXECUTABLE);
          if (n >= 0) {
            f = new File(folderName, z.getName().substring(0, n));
            isExecutable = true;
          } else {
            f = new File(folderName, z.getName());
            isExecutable = false;
          }
          f.getParentFile().mkdirs();
          out = new BufferedOutputStream(new FileOutputStream(f));
          bufferedWrite(in, out);
          out.close();
          if (isExecutable) {
            f.setExecutable(true, false);
          }
        }
      }
      in.close();
    } catch (Exception ex) {
      log0(-1, "unpackJar: " + ex.getMessage());
      return false;
    }
    log0(lvl, "unpackJar: completed");
    return true;
  }

  private static void addToJar(JarOutputStream jar, File dir, String prefix) throws IOException {
    File[] content;
    prefix = prefix == null ? "" : prefix;
    if (dir.isDirectory()) {
      content  = dir.listFiles();
      for (int i = 0, l = content.length; i < l; ++i) {
        if (content[i].isDirectory()) {
          jar.putNextEntry(new ZipEntry(prefix + (prefix.equals("") ? "" : "/") + content[i].getName() + "/"));
          addToJar(jar, content[i], prefix + (prefix.equals("") ? "" : "/") + content[i].getName());
        } else {
          addToJarWriteFile(jar, content[i], prefix);
        }
      }
    } else {
      addToJarWriteFile(jar, dir, prefix);
    }
  }
  
  private static void addToJarWriteFile(JarOutputStream jar, File file, String prefix) throws IOException {
    if (file.getName().startsWith(".")) {
      return;
    }
    String suffix = "";
    if (file.canExecute()) {
      suffix = EXECUTABLE;
    }
    jar.putNextEntry(new ZipEntry(prefix + (prefix.equals("") ? "" : "/") + file.getName() + suffix));
    FileInputStream in = new FileInputStream(file);
    bufferedWrite(in, jar);
    in.close();
  }

  public interface JarFileFilter {
    public boolean accept(ZipEntry entry);
  }

  public interface fileFilter {
    public boolean accept(File entry);
  }

  private static synchronized void bufferedWrite(InputStream in, OutputStream out) throws IOException {
    byte[] buffer = new byte[1024 * 512];
    int read;
    while (true) {
      read = in.read(buffer);
      if (read == -1) {
        break;
      }
      out.write(buffer, 0, read);
    }
    out.flush();
  }
  
  public static boolean pathEquals(String path1, String path2, boolean isFolder) {
    String p1 = new File(slashify(path1, isFolder)).getAbsolutePath();
    String p2 = new File(slashify(path2, isFolder)).getAbsolutePath();
    if (Settings.isWindows()) {
      if (p1.startsWith("/")) {
        p1 = p1.substring(1);
      }
      if (p2.startsWith("/")) {
        p2 = p2.substring(1);
      }
      p1 = p1.toUpperCase();
      p2 = p2.toUpperCase();
    }
    return p1.equals(p2);
  }
}

