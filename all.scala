import sys.process._

package sam.src.db
{
object basis_info {
val datafileloc="/u01/oracle/oradata/a015/*"
val removefiles=s"""rm -rf $datafileloc"""
val nowday= new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date)
val realtime= new java.text.SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_SSS").format(new java.util.Date)
val filepath="/u01/orabackup/FRABK/autobackup/"
val logpath0="/u01/orabackup/FRABK/logs/"+"RMAN_ApplyArch_"+realtime+".log"
val concat0="""(\w+)-([\w\.]+)-"""+nowday+"""-([\w\.]+).bkp"""
}


object ChkCnt {
def ChkcntGetVal = {
val files = new java.io.File(basis_info.filepath).listFiles
val scala1 = files map (_.getName) filter(_.matches(basis_info.concat0))
if (! scala1.isEmpty) Some(scala1.max).get else "None"
}
}
object DBOperation {
//shutdown abort
def resShutdownDB = Seq("/bin/sh", "-c", """sqlplus '/as sysdba' <<EOF
shutdown abort;
exit
EOF""").!
//startup nomount
def resStartnomount = Seq("/bin/sh", "-c", """sqlplus '/as sysdba' <<EOF
startup nomount;
exit
EOF""").!

//alter database mount
def resAltmount = Seq("/bin/sh", "-c", """sqlplus '/as sysdba' <<EOF
alter database mount;
exit
EOF""").!
//restore controlfile
def restorectl(loc :String,x :String)= {
val concat2=s"""rman target / nocatalog <<EOF
restore controlfile from '$loc$x';
alter database mount;
exit;
EOF"""
Seq("/bin/sh", "-c",concat2 ).!
}

//restore datafile
def restoredatafile {
val concat3=s"""rman target / nocatalog <<EOF
restore database;
exit;
EOF"""
Seq("/bin/sh", "-c",concat3 ).!
}

//apply archive until cancel automatically
def ApplyArchAuto(log :String) = Seq("/bin/sh", "-c", s"""sqlplus '/as sysdba' <<EOF > $log
recover database using backup controlfile until cancel;
auto
exit
EOF""").!

}

object OSCmd {
//remove OS files
def resRemovedata(s1 :String) = Seq("/bin/sh", "-c", s1).!
}


//---for package
}
