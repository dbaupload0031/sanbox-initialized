import sys.process._
import sam.src.db._

package sam.exec.db
{
//-------package
object Main001 {
  def main(args: Array[String]) {
  val resChkcntGetVal=ChkCnt.ChkcntGetVal
  if (resChkcntGetVal!="None") {
  val res0=DBOperation.resShutdownDB
  if (res0==0)  println("resShutdownDB has been closed successfully.")
  val res1=OSCmd.resRemovedata(basis_info.removefiles)
  if (res1==0) println("resRemovedata has been closed successfully.")
  println(resChkcntGetVal)
  DBOperation.resStartnomount
  DBOperation.restorectl(basis_info.filepath,resChkcntGetVal)
  DBOperation.resAltmount
  DBOperation.restoredatafile
  }
}
}
