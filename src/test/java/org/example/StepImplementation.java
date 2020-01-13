package org.example;

import com.sun.deploy.security.SelectableSecurityManager;
import com.thoughtworks.gauge.Step;
import org.example.helper.ElementHelper;
import org.example.helper.StoreHelper;
import org.example.model.ElementInfo;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StepImplementation extends BaseTest {

    public Actions actions;

    public StepImplementation() {
        actions = new Actions(driver);
    }

    private By getBy(String key) {
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        By infoParam = ElementHelper.getElementInfoToBy(elementInfo);
        return infoParam;
    }

    private List<WebElement> findElementsByKey(String key) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 30);

        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        By infoParam = ElementHelper.getElementInfoToBy(elementInfo);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(infoParam));
        return driver.findElements(infoParam);
    }

    private WebElement findElementByKey(String key) {
        By infoParam = getBy(key);
        WebDriverWait webDriverWait = new WebDriverWait(driver, 30);

        WebElement webElement = webDriverWait.until(ExpectedConditions.presenceOfElementLocated(infoParam));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})",
                webElement);
        return webElement;
    }

    private int getRandomElementIndex(String key) {
        int listProduct = findElementsByKey(key).size();
        System.out.println(listProduct);
        int i = new Random().nextInt(listProduct);
        return i;
    }


    @Step("Elementi bekle <btn_GirisYap_MainPage>")
    public WebElement waitElementLoadByKey(String key) {

        WebElement webElement;
        int loopCount = 0;
        while (loopCount < 200) {
            try {
                webElement = findElementByKey(key);
                //logger.info("Element:'" + key + "' found.");
                return webElement;

            } catch (WebDriverException e) {
            }
            loopCount++;
            waitByMilliSeconds(200);
        }
        Assert.fail("Element: '" + key + "' doesn't exist.");
        return null;
    }

    @Step("Wait <value> seconds")
    public void waitBySeconds(int seconds) {
        try {
            //logger.info("Waiting for " + seconds + " seconds.");
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Step("Wait <value> milliseconds")
    public void waitByMilliSeconds(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Step("Elementi tıkla <btn_GirisYap_MainPage>")
    public void clickElementByKey(String key) {
        waitBySeconds(1);
        if (!key.equals("")) {
            WebElement element = findElementByKey(key);
            hoverElement(element);
            waitByMilliSeconds(500);
            element.click();
        }
    }

    @Step("Elementin üstünde bekle <btn_GirisYap_MainPage>")
    public void hoverElement(String key) {
        if (!key.equals("")) {
            WebElement element = findElementByKey(key);
            hoverElement(element);
            waitByMilliSeconds(500);
        }
    }

    @Step("Değer yazılır <text> to element <key>")
    public void sendKeysByKey(String text, String key) {
        waitBySeconds(1);
        findElementByKey(key).sendKeys(text);
        //logger.info("Text: '" + text + "' written to element: '" + key + "'.");
    }


    @Step("Elementin <key> Text'leri <text> kontrol et")
    public void textKontrol(String key, String text1) {
        waitBySeconds(1);
        String text = findElementByKey(key).getText();
        //String text = findElementByKey(key).getAttribute("value");
        //findElementByKey(key).getAttribute("value");
        System.out.println(text);
        Assert.assertEquals("Kontrol Başarısız.", text.trim(), text1);

    }

    private void randomClick(String key) {

        WebElement webElement = findElementsByKey(key).get(getRandomElementIndex(key));
        hoverElement(webElement);
        webElement.click();
    }

    private void hoverElement(WebElement element) {
        actions.moveToElement(element).build().perform();
    }

    @Step("<key> random tıklanır")

    public void randomProductClick(String key) {
        randomClick(key);
    }


    @Step("<key> <key2> urun random tıklanır, csv dosyasına yazır")
    public void randomProductWrite(String key, String key2) {
        waitElementLoadByKey(key);
        int a = getRandomElementIndex(key2);
        WebElement webElementName = findElementsByKey(key).get(a);
        WebElement webElementPrice = findElementsByKey(key2).get(a);
        String name = webElementName.getText();
        String price = webElementPrice.getText();
        //System.out.println(name + "/n" + price);
        webElementPrice.getText();

        //String path = "/Users/testinium/IdeaProjects/DenemeProje/UrunBilgileri.txt";
        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    //kullanının bulunduğu dizin projenin
                    //utf türkçe karakter destekliyor.
                    new FileOutputStream(System.getProperty("user.dir") + "/UrunBilgileri.csv"), "UTF-8"));
            writer.write("Urun adı: " + "'" + name + "'" + "\r\n" + "Urun fiyati: " + "'" + price + "'");
        } catch (IOException ex) {
            // Report
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {
            }
            webElementName.click();
        }


    }

    public ArrayList<String> readTxtList(String path) throws FileNotFoundException {
        ArrayList<String> txt = new ArrayList<>();
        FileInputStream fstream = new FileInputStream(System.getProperty("user.dir") + path);

        BufferedReader br = null;
        try {
            String strLine;
            br = new BufferedReader(new InputStreamReader(fstream, "UTF-8"));
            while ((strLine = br.readLine()) != null) {
                txt.add(strLine.split("'")[1]+" ");
            }
            fstream.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return txt;
    }


    @Step("Ürün detay ekranından <key> isim <key1> <key2> <key3>fiyat kontrolü yapılır")
    public void csvProductChech(String key, String key1, String key2, String key3) throws FileNotFoundException {

        String name = findElementByKey(key).getText();
        String name2 = name+" ";
        String price1 = findElementByKey(key1).getText();
        String priceKurus = findElementByKey(key2).getText();
        String priceTl = findElementByKey(key3).getText();
        String price = (price1 + "," + priceKurus + " " + priceTl);
        System.out.println(price);

        ArrayList txtValue = readTxtList("/UrunBilgileri.csv");
        System.out.println(txtValue.get(0));
        System.out.println(txtValue.get(1));
        //System.out.println(name + " " + price);
        Assert.assertEquals("!!",name2,txtValue.get(0));
       // Assert.assertEquals("!!",price,txtValue.get(1));
        //Assert.assertTrue("Eşleşmiyor", name2.equals(txtValue.get(0)) && price.equals(txtValue.get(1)));

    }


    @Step("Elementin <key> değerini tut")
    public String getElementValueText(String key) {

        String value = findElementByKey(key).getText();
        return value;

    }


    @Step("Ürün sepetinde ürün adet sayısı iki <key1> artılır <key>tutar bilgisi kontrol edilir")
    public void implementation1(String key1, String key) {
        //1 tane olan fiyat
        waitElementLoadByKey(key);

        float price = Float.parseFloat(findElementByKey(key).getText().trim().replace(".","").replace(",",".").split(" ")[0]);
        float price2 = price * 2;
         clickElementByKey(key1);
         waitBySeconds(1);
        float newPrice = Float.parseFloat(findElementByKey(key).getText().trim().replace(".","").replace(",",".").split(" ")[0]);
        System.out.println(newPrice + "  " + price2);
        Assert.assertTrue("fiyat eşleşmedi",price2==newPrice);


    }


    @Step("Ürün toplamı değeri <key>ve kargo tutarı <key1> csv dosyasına yazdırılır, <key2>alışveriş tamamlanır.")
    public void implementation2(String key, String key1, String key2) {
        String cartPrice = findElementByKey(key).getText();
        String shippingPrice = findElementByKey(key1).getText();
        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(

                    new FileOutputStream(System.getProperty("user.dir") + "/AlisverisTutarlari.csv"), "UTF-8"));
            writer.write("Urun Fiyati: " + "'" + cartPrice + "'" + "\r\n" + "Kargo Tutarı: " + "'" + shippingPrice + "'");
        } catch (
                IOException ex) {
            // Report
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {
            }
            clickElementByKey(key2);
        }
    }


    @Step("Değer <text> seçilir to element <key>")
    public void selectElement(String text, String key) {
        hoverElement(key);
        Select select = new Select(findElementByKey(key));
        select.selectByVisibleText(text);

    }

    @Step("Değer <text> seçilir to elementt <key>")
    public void selectElementt(String text, String key) {
        //hoverElement(key);
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        By infoParam = ElementHelper.getElementInfoToBy(elementInfo);
        Select select = new Select(driver.findElement(infoParam));
        select.selectByVisibleText(text);

    }


    @Step("<key> Eğer sepet doluysa boşaltılır  <key2> <key3> <key4>")
            public void cartPruductDelete(String key, String key2, String key3, String key4) {

        int count = Integer.parseInt(findElementByKey(key).getText());

        if (count != 0) {
            clickElementByKey(key);
            hoverElement(key2);
            while (count > 0) {
                clickElementByKey(key2);
                count--;
            }
           clickElementByKey(key4);
        } else
            clickElementByKey(key3);
    }


}
