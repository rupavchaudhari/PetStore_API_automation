package api.utilities;

import org.testng.annotations.DataProvider;

public class DataProviders {

	@DataProvider(name = "data")
	public Object[][] getAllData() throws Exception {
	    String path = System.getProperty("user.dir") + "//TestData//TestData.xlsx";
	    XLUtility xl = new XLUtility(path);

	    int rowCount = xl.getRowCount("Sheet1");
	    int colCount = xl.getCellCount("Sheet1", 1); // should be 7

	    Object[][] apidata = new Object[rowCount][colCount];

	    for (int i = 1; i <= rowCount; i++) {
	        for (int j = 0; j < colCount; j++) {
	            apidata[i - 1][j] = xl.getCellData("Sheet1", i, j); // must return String
	        }
	    }
	    return apidata;
	}
            
	
	@DataProvider(name = "UserNames")
	public Object[][] getUserNames() throws Exception {
	    String path = System.getProperty("user.dir") + "//TestData//TestData.xlsx";
	    XLUtility xl = new XLUtility(path);

	    int rowCount = xl.getRowCount("Sheet1");
	    Object[][] apidata = new Object[rowCount][1];

	    for (int i = 1; i <= rowCount; i++) {
	        apidata[i - 1][0] = xl.getCellData("Sheet1", i, 1); // username column
	    }
	    return apidata;
	}

	@DataProvider(name = "petdata")
	public Object[][] getSheetPetData() throws Exception {
	    String path = System.getProperty("user.dir") + "//TestData//PetData.xlsx";
	    XLUtility xl = new XLUtility(path);

	    int rowCount = xl.getRowCount("Sheet1");
	    int colCount = xl.getCellCount("Sheet1", 1); 

	    Object[][] data = new Object[rowCount][colCount];

	    for (int i = 1; i <= rowCount; i++) {
	        for (int j = 0; j < colCount; j++) {
	            data[i - 1][j] = xl.getCellData("Sheet1", i, j); // must return String
	        }
	    }
	    return data;
	}
	}
	
