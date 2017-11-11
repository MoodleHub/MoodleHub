package moodlehub

object Main {

  def main(args: Array[String]): Unit = {
//    Client.getSiteInfo
    Client.getUsersCourses(userId = 219044)
//      Client.getContents(courseId = 13768)

//    FileManager.fileDownloader("https://moodle.epfl.ch/webservice/pluginfile.php/1540933/mod_resource/content/0/chapter%203.pdf?token=6aca2ab143095b1e8498c6e8c3364898",
//    "tmp/a.pdf")
  }

}
