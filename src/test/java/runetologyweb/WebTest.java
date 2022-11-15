package runetologyweb;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebTest {

    private static WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearsDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void validTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Петров-Иванов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] checkbox")).click();
        driver.findElement(By.cssSelector("[data-test-id='agreement'] button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());

    }

    @Test
    void invalidNameSurnameTest1() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Petrov");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] checkbox")).click();
        driver.findElement(By.cssSelector("[data-test-id='agreement'] button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'] input")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());

    }

    @Test
    void invalidNameSurnameTest2() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] checkbox")).click();
        driver.findElement(By.cssSelector("[data-test-id='agreement'] button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'] input")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());

    }

    @Test
    void invalidPhoneTest1() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Петров-Иванов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] checkbox")).click();
        driver.findElement(By.cssSelector("[data-test-id='agreement'] button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'] input")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());

    }

    @Test
    void invalidPhoneTest2() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Петров-Иванов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("glsgl6546465");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] checkbox")).click();
        driver.findElement(By.cssSelector("[data-test-id='agreement'] button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'] input")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());

    }

    @Test
    void emptyCheckbox() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Петров-Иванов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id]")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text.trim());

    }
}
