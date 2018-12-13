import com.eviware.soapui.support.XmlHolder
import java.io.File
import java.io.IOException
import jxl.*
import jxl.read.biff.BiffException
import jxl.write.*
import jxl.write.Label
import hudson.model.*

log.info("Testing Started")
def reqOperationName = "Operation Name"
def inputDataFileName = "FileLocation/filename.xls"
def inputDataSheetName = "Datasheet name"
Workbook workbook = Workbook.getWorkbook(new File(inputDataFileName))
Sheet  sheet1 = workbook.getSheet(inputDataSheetName)


def myList = new ArrayList<String>();
def groovyUtils = new com.eviware.soapui.support.GroovyUtils(context)
String xmlResponse = reqOperationName+"#Request"
def reqholder = groovyUtils.getXmlHolder(xmlResponse)
try{
    rowcount = sheet1.getRows()
    colcount = sheet1.getColumns()

    for(Row in 1..rowcount-1){

    String reqTagName = sheet1.getCell(0,0).getContents()

    def TagCount = reqholder["count(//*:"+reqTagName+")"]

    if(TagCount!=0){
        String reqTagValue = sheet1.getCell(0,Row).getContents()
        if(reqTagValue!=null && !reqTagValue.isEmpty() && reqTagValue!="")
                {
                    reqholder = groovyUtils.getXmlHolder(xmlResponse)
                    log.info "extracted value : " + reqTagValue
                reqholder.setNodeValue("//*:"+reqTagName, reqTagValue)
                reqholder.updateProperty()        
                log.info "node value : " + reqholder.getNodeValue("//*:"+reqTagName)
                //test the request
                testRunner.runTestStepByName(reqOperationName)
                reqholder = groovyUtils.getXmlHolder(reqOperationName+"#Response")
                myList.add(reqholder.getPrettyXml().toString())
                log.info myList[Row-1]
                }                      
    }

    }
}
catch (Exception e) {log.info(e)}
finally{
    workbook.close()
}
Workbook existingWorkbook = Workbook.getWorkbook(new File(inputDataFileName));
WritableWorkbook workbookCopy = Workbook.createWorkbook(new File(inputDataFileName), existingWorkbook);

try
{
    WritableSheet sheetToEdit = workbookCopy.getSheet(inputDataSheetName);
    WritableCell cell;
    for (int i =1;i<myList.size();i++)
    {
    def sheetdata = System.getenv(myList[i])

    resTagValue1= sheetdata.getNodeValue("//*:Response Field Element1")
    Label l = new Label(2, i+1, resTagValue1.toString());
    cell = (WritableCell) l;
    sheetToEdit.addCell(cell);

    resTagValue2= sheetdata.getNodeValue("//*:Response Field Element2")
    Label m = new Label(3, i+1, resTagValue2.toString());
    cell = (WritableCell) m;
    sheetToEdit.addCell(cell);

    resTagValue3= sheetdata.getNodeValue("//*:Response Field Element3")
    Label n = new Label(4, i+1, resTagValue3.toString());
    cell = (WritableCell) n;
    sheetToEdit.addCell(cell);

    resTagValue4= sheetdata.getNodeValue("//*:Response Field Element4")
    Label o = new Label(5, i+1, resTagValue4.toString());
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
log.info("Testing Over")
