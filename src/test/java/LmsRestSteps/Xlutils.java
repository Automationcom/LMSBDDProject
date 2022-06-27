package LmsRestSteps;


	import java.io.FileInputStream;

	import org.apache.poi.xssf.usermodel.XSSFRow;
	import org.apache.poi.xssf.usermodel.XSSFSheet;
	import org.apache.poi.xssf.usermodel.XSSFWorkbook;

	public class Xlutils {

		public static Object[][] readExcelData(String filePath, String sheetName) throws Exception
		{
			System.out.println(filePath);
			XSSFWorkbook wb = null;
			XSSFSheet sheet;
			FileInputStream fis = null;
			Object data[][] = null;
			try {
				fis = new FileInputStream(filePath);
				wb = new XSSFWorkbook(fis);
				sheet = wb.getSheet(sheetName);
				int rowCount = sheet.getPhysicalNumberOfRows();
				XSSFRow row = sheet.getRow(0);
				int colCount = row.getLastCellNum();
				
				data = new Object[rowCount-1][colCount];
				for (int i = 0; i < rowCount - 1; i++) {
					row = sheet.getRow(i + 1);
					for (int j = 0; j < colCount; j++) {
						data[i][j] = row.getCell(j).toString();
						//System.out.println(row.getCell(j));
					}
				} 
			} 
			catch(Exception ex) 
			{
				ex.printStackTrace();
				
			}finally {
				// TODO: handle finally clause
				wb.close();
				fis.close();
			}
			return data;
		}	
		}

