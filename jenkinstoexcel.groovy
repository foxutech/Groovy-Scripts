import com.eviware.soapui.support.XmlHolder
import java.io.File
import java.io.IOException
import jxl.*
import jxl.read.biff.BiffException
import jxl.write.*
import jxl.write.Label
import hudson.model.*

log.info("Started")
def reqOperationName = "Operation Name"
def inputDataFileName = "FileLocation/filename.xls"
def inputDataSheetName = "Datasheet name"
Workbook workbook = Workbook.getWorkbook(new File(inputDataFileName))
Sheet  sheet1 = workbook.getSheet(inputDataSheetName)

def State          = params.State
def zip            = params.zip
def Age            = params.Age
def Effective_Date = params.Effective_Date
def Plan_Code      = params.Plan_Code

def myList = new ArrayList<String>();
def groovyUtils = new com.eviware.soapui.support.GroovyUtils(context)

Workbook existingWorkbook = Workbook.getWorkbook(new File(inputDataFileName));
WritableWorkbook workbookCopy = Workbook.createWorkbook(new File(inputDataFileName), existingWorkbook);

try
{
    WritableSheet sheetToEdit = workbookCopy.getSheet(inputDataSheetName);
    WritableCell cell;
    for (int i =1;i<myList.size();i++)
    {
    def sheetdata = System.getenv(myList[i])

    resTagValue1= sheetdata.getNodeValue("State")
    Label l = new Label(2, i+1, resTagValue1.toString());
    cell = (WritableCell) l;
    sheetToEdit.addCell(cell);

    resTagValue2= sheetdata.getNodeValue("zip")
    Label m = new Label(3, i+1, resTagValue2.toString());
    cell = (WritableCell) m;
    sheetToEdit.addCell(cell);

    resTagValue3= sheetdata.getNodeValue("Age")
    Label n = new Label(4, i+1, resTagValue3.toString());
    cell = (WritableCell) n;
    sheetToEdit.addCell(cell);

    resTagValue4= sheetdata.getNodeValue("Effective_Date")
    Label o = new Label(5, i+1, resTagValue4.toString());
    cell = (WritableCell) o;
    sheetToEdit.addCell(cell);
	
	resTagValue5= sheetdata.getNodeValue("Plan_Code")
    Label o = new Label(6, i+1, resTagValue4.toString());
    cell = (WritableCell) o;
    sheetToEdit.addCell(cell);
    }
}
catch (Exception e) {log.info(e)}
finally{
     workbookCopy.write();
 workbookCopy.close();
 existingWorkbook.close();
}
log.info("Completed")
