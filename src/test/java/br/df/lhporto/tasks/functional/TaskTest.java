package br.df.lhporto.tasks.functional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TaskTest {
	
	public WebDriver acessarAplicacao() throws Exception {
		// WebDriver driver = new ChromeDriver();
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		WebDriver driver = new RemoteWebDriver(new URL("http://192.168.1.5:4444/wd/hub"), cap);
		driver.navigate().to("http://localhost:8001/tasks/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() throws Exception {
		WebDriver driver = acessarAplicacao();
		try {
			driver.findElement(By.id("addTodo")).click();		
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			driver.findElement(By.id("dueDate")).sendKeys("21/11/2022");
			driver.findElement(By.id("saveButton")).click();
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Success!", message);
		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao() throws Exception {
		WebDriver driver = acessarAplicacao();
		try {
			driver.findElement(By.id("addTodo")).click();		
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");
			driver.findElement(By.id("saveButton")).click();
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the task description", message);
		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemData() throws Exception {
		WebDriver driver = acessarAplicacao();
		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			driver.findElement(By.id("saveButton")).click();
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the due date", message);
		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() throws Exception {
		WebDriver driver = acessarAplicacao();
		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2010");
			driver.findElement(By.id("saveButton")).click();
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Due date must not be in past", message);
		} finally {
			driver.quit();
		}
	}

}
