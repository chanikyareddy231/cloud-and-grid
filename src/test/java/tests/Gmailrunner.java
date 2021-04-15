package tests;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.sql.Driver;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Gmailrunner 
{
	public static void main(String[] args) throws Exception
	{
	   File f=new File("src//test//resources//Book1.xlsx");
	   FileInputStream fi=new FileInputStream(f);
	   Workbook wb=WorkbookFactory.create(fi);
	   Sheet sh1=wb.getSheet("Sheet");
	   Sheet sh2=wb.getSheet("Sheet2");
	   int nour1=sh1.getPhysicalNumberOfRows();
	   int nour2=sh2.getPhysicalNumberOfRows();
	   int nouc1=sh1.getRow(0).getLastCellNum();
	   int nouc2=sh2.getRow(0).getLastCellNum();
	   Gmailmethods gm=new Gmailmethods();
	   int flag=0;
	   for(int i=1;i<nour1;i++)
	   {
		   DataFormatter df=new DataFormatter();
		   String mode=df.formatCellValue(sh1.getRow(i).getCell(2));
		   if(mode.equalsIgnoreCase("yes"))
		   {
			   String tid=df.formatCellValue(sh1.getRow(i).getCell(0));
			   for(int j=1;j<nour2;j++)
			   {
				   String sid=df.formatCellValue(sh2.getRow(j).getCell(0));
				   String method=df.formatCellValue(sh2.getRow(j).getCell(2));
				   String e=df.formatCellValue(sh2.getRow(j).getCell(3));
				   String d=df.formatCellValue(sh2.getRow(j).getCell(4));
				   String c=df.formatCellValue(sh2.getRow(j).getCell(5));
				   if(tid.equalsIgnoreCase(sid))
				   {
					   	Method methodarray[]=Driver.class.getMethods();
					    for(int k=0;k<methodarray.length;k++)
					    {
					       if(methodarray[k].getName().equals(method))
					      {
					    	   String r=(String) methodarray[k].invoke(gm,e,d,c) ;
					    	   sh2.getRow(j).createCell(nouc2).setCellValue(r);
					    	   if(r.contains("Failed") | r.contains("failed") | 
					    			          r.contains("interrupted") | r.contains("Interrupted"))
					    	  {
					    		   flag=1;
					    		   sh1.getRow(i).createCell(nouc1).setCellValue("failed");
					    	  }
					    	  break;
					      }
					    }
				   }
				   else
				   {
					   break;
				   }
			   }
		   }
		   else
		   {
			   break;
		   }
	   }
	   
	}

}
