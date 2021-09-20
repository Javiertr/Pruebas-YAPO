package TestSelenium;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BusquedaDeProductos {
	
	private WebDriver driver;
	
	//Variables globales
	private EscribirExcel writeFile;
	private leerarchivoexcel readfile;
	private By searchBoxLocator = By.xpath("//*[@id=\"searchalgolia\"]/div/div/div/div/div/form/div/input");
	private By searchBtnLocator = By.xpath("//*[@id=\"searchalgolia\"]/div/div/div/div/div/form/div/button/i");
	private By productBtnLocator = By.xpath("//*[@id=\"productsalgolia\"]/div/div/div[2]/div[2]/div/ol/li[1]/div/div/div[3]");
	private By nameproductLocator = By.xpath("//*[@id=\"id_ficha_producto\"]/div[3]/div[2]/div[1]");
	private By codigoproductoLocator = By.xpath("//*[@id=\"id_ficha_producto\"]/div[3]/div[2]/div[2]/p[1]/span");
	private By precioproductLocator = By.xpath("//*[@id=\"id_ficha_producto\"]/div[3]/div[3]/div[1]/div");
	private By disponibilidadentiendaLotactor = By.xpath("//*[@id=\"id_ficha_producto\"]/div[3]/div[6]/div[2]/div[2]/p");
	private By disponibilidadsitiowebLocator = By.xpath("//*[@id=\"id_ficha_producto\"]/div[3]/div[6]/div[1]/div[2]/p");
	
	
	@Before
	public void setUp() throws Exception {
		  //Cambiar acá la ruta de Chromedriver
		  System.setProperty("webdriver.chrome.driver", "C:/Drivers/chromedriver.exe");
		  
		  driver = new ChromeDriver(); 
		  readfile = new leerarchivoexcel();
		  writeFile = new EscribirExcel();
		  
		  driver.manage().window().maximize();
		  driver.get("https://www.pcfactory.cl/");
		
	}

	@After
		
	public void tearDown() {
		driver.quit();
	}

	@Test
	public void test() throws IOException, InterruptedException {
		
		//Cambiar acá la ruta del excel
		String filepath = "C:\\Users\\javie\\Escritorio\\librodepruebas.xlsx";
		
		int rowCount = readfile.getNumRow(filepath, "Sheet1");
		
		for (int x = 1; x <= rowCount; x++) {
			
			//Lectura del archivo
			String searchText = readfile.getCellValue(filepath, "Sheet1", x, 0);
			driver.findElement(searchBoxLocator).sendKeys(searchText);
			driver.findElement(searchBtnLocator).click();
			
			Thread.sleep(3000); 
			
			driver.findElement(productBtnLocator).click();
			Thread.sleep(3000);
			
			//Escritura del archivo
			String resulText = "";
			resulText = driver.findElement(nameproductLocator).getText();
			writeFile.writeCellValue(filepath, "Sheet1", x, 1, resulText);
			resulText = driver.findElement(codigoproductoLocator).getText();
			writeFile.writeCellValue(filepath, "Sheet1", x, 2, resulText);
			resulText = driver.findElement(precioproductLocator).getText();
			writeFile.writeCellValue(filepath, "Sheet1", x, 3, resulText);
			resulText = driver.findElement(disponibilidadentiendaLotactor).getText();
			writeFile.writeCellValue(filepath, "Sheet1", x, 4, resulText);
			resulText = driver.findElement(disponibilidadsitiowebLocator).getText();
			writeFile.writeCellValue(filepath, "Sheet1", x, 5, resulText);
			
			
		}
		
		
	}

}
