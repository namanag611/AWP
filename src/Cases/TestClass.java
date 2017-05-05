package Cases;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import Core.CreateDriver;
import POM.Page1;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class TestClass extends CreateDriver {


	int AnyError = 0;//need to remove
	int k = 1;//need to remove

	String Category="";
	String Summary="";
	ResourceBundle rb = ResourceBundle.getBundle("Elements"); // Get elements of web page from property file
	Logger log = Logger.getLogger("devpinoyLogger"); // To generate the log file

	String dir = System.getProperty("user.dir");
	String file_path = dir+"\\datafile\\20170302_Ticket_0023001_Testing_Report_Dev_Server.xls";

	String ScreenShot_path = dir+"\\ScreenShot\\";
	String currentdate="";
	String updatefilename="";

	@Test
	@Parameters("task")
	public void Createtask(String task) throws InterruptedException
	//public void Createtask()
	{
		//System.out.println("Started Script for: "+task);
		fd.get(rb.getString("url"));
		Page1 p=new Page1(fd);
		p.EnterUserName("namanagrawal");
		p.EnterPassword("PopcorN");
		p.ClickLogin();
		Thread.sleep(2000);
		p.findtasknumber(task);
		Category=p.getcategory();
		System.out.println("Category " +Category);
		Summary=p.getsummary();
		System.out.println("Summary " +Summary);
		createdirectory(task);
		copytestreporttofolder(task);
		renamefile(task);
		updateinxlsfile(Category, Summary, updatefilename);
	}



	public void createdirectory(String tasknumber)
	{
		try
		{
			File file = new File(rb.getString("workpath")+tasknumber);
			if (!file.exists()) {
				if (file.mkdir()) {
					log.debug("Directory is created for: "+tasknumber);
				} else {
					System.out.println("Failed to create directory for: "+tasknumber+"as already exists.");
				}
			}
		}
		catch(Exception ex)
		{
			log.debug("Exception occured in method 'createdirectory': "+ex);
		}
	}

	public void copytestreporttofolder(String tasknumber)
	{
		try
		{
			File source = new File(file_path);
			File dest = new File(rb.getString("workpath")+tasknumber+"\\20170302_Ticket_0023001_Testing_Report_Dev_Server.xls");
			try {
				FileUtils.copyFile(source, dest);
				log.debug("File copied to location.");
			} catch (Exception ex) {
				log.debug("Exception occured in copying file: "+ex);
			}
		}
		catch(Exception ex)
		{
			log.debug("Exception occured in method 'copytestreporttofolder': "+ex);
		}
	}

	public String generatefilename(String tasknumber)
	{
		String filename="";
		try
		{
			currentdate="";
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			LocalDate localDate = LocalDate.now();
			currentdate= dtf.format(localDate);
			String formattedcurrentdate = currentdate.replaceAll("\\/", "");
			filename=formattedcurrentdate+"_Ticket_"+tasknumber+"_Testing_Report_Dev_Server.xls";
		}
		catch(Exception ex)
		{
			log.debug("Exception occured in method 'generatefilename': "+ex);
		}   
		return filename;
	}

	public void renamefile(String tasknumber)
	{
		try
		{
			String filetorename = rb.getString("workpath")+"\\"+tasknumber+"\\20170302_Ticket_0023001_Testing_Report_Dev_Server.xls";
			String filenametoset = generatefilename(tasknumber);
			File oldName = new File(filetorename);
			File newName = new File(rb.getString("workpath")+"\\"+tasknumber+"\\"+filenametoset);
			updatefilename=rb.getString("workpath")+"\\"+tasknumber+"\\"+filenametoset;
			if(oldName.renameTo(newName)) {
				log.debug("File is renamed.");
			} else {
				log.debug("Error occurred in renaming the file.");
			}
		}
		catch(Exception ex)
		{
			log.debug("Exception occured in method 'renamefile': "+ex);
		}
	}


	public void updateinxlsfile(String category, String summary, String file) {
		try {
			Workbook workbook = Workbook.getWorkbook(new File(file));
			// create a new excel and copy from existing
			WritableWorkbook copy = Workbook.createWorkbook(new File(file), workbook);
			WritableSheet sheet = copy.getSheet(0);
			Label cat = new Label(2, 0, category);//Column Row start at 0
			sheet.addCell(cat);
			Label sum = new Label(2, 1, summary);
			sheet.addCell(sum);
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			LocalDate localDate = LocalDate.now();
			String date= dtf.format(localDate);
			
			WritableSheet sheet1 = copy.getSheet(1);
			Label date1 = new Label(7, 1, date);
			sheet1.addCell(date1);
			Label date2 = new Label(8, 1, date);
			sheet1.addCell(date2);
			
			copy.write();
			copy.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			log.debug("Exception Occured while generating the result: " + e.getMessage());
			Assert.fail();
		}
	}

}
