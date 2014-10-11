package ixlib

import java.io._
import java.awt.image._
import javax.imageio._
import org.apache.pdfbox._
import org.apache.pdfbox.pdmodel._
import org.apache.pdfbox.pdmodel.graphics.xobject._
import org.apache.pdfbox.pdfparser._

import scala.collection.JavaConverters._
import scala.Array.canBuildFrom
import scala.annotation.tailrec

object FileIO {

  val DefaultCset = "UTF-8"

  def input[T](fname: String)(f: BufferedReader => T): T = input(new File(fname))(f)

  def input[T](file: File, cset: String = DefaultCset)(f: BufferedReader => T): T = {
    val br = this.br(file, cset)
    try {
      f(br)
    } finally {
      br.close
    }
  }

  @deprecated
  def inputList[T](fname: String): List[String] = input(fname) { br =>
    br2Itrt(br).toList
  }

  @deprecated
  protected def br2Itrt(br: BufferedReader): Iterator[String] =
    Iterator.continually(br.readLine).takeWhile(_ != null)

  /** output */
  def output[T](fname: String)(f: PrintWriter => T): T = output(new File(fname))(f)

  def output[T](file: File, cset: String = DefaultCset, append: Boolean = false)(f: PrintWriter => T): T = {
    val pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, append)))

    try {
      f(pw)
    } finally {
      pw.close
    }
  }

  /** image output */
  def imgout(bi: BufferedImage, fname: String, format: String = "PNG") {
    import java.awt.image.BufferedImage;
    import javax.imageio.{ImageIO => JImageIO}

    if (!ImageIO.write(bi, format, new File(fname))) {
      throw new IOException("フォーマットが対象外")
    }
  }

  // pdf2BufferedImages
  def pdf2BufferedImages(pdfFName: String): Seq[BufferedImage] = {
    var pdf: PDDocument = null
    var pdfStream: FileInputStream = null

    try {

      pdfStream = new FileInputStream(pdfFName)
      val pdfParser = new PDFParser(pdfStream)
      pdfParser.parse
      pdf = pdfParser.getPDDocument

      var imageCounter = 1
      val pages = pdf.getDocumentCatalog.getAllPages.asScala

      val biList = pages.flatMap {
        case page: PDPage =>

          val resources = page.getResources
          val images = resources.getImages

          if (images != null) {
            images.keySet.asScala.map {
              case key: String =>
                images.get(key).getRGBImage
            }
          } else {
            Nil
          }
      }
      biList

    } catch {
      case _: Exception => Nil
    } finally {
      if (pdfStream != null) pdfStream.close()
      if (pdf != null) pdf.close()
    }
  }

  private def br(file: File, cset: String = DefaultCset): BufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), cset))

  private def br(fname: String): BufferedReader = br(new File(fname))
}
