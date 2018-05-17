package com.daniel.model;

import javax.json.JsonObject;
import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.Arrays;

@Entity
@Transactional

@Table(name = "WEB_PAGE")
public class WebPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "url_info")
    private String urlInfo;

    @Column(name = "url_content")
    private char[] urlContent;

    @Column(name = "addition_date")
    @NotNull
    private LocalDate additionDate;

    public WebPage() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlInfo() {
        return urlInfo;
    }

    public void setUrlInfo(String urlInfo) {
        this.urlInfo = urlInfo;
    }

    public char[] getUrlContent() {
        return urlContent;
    }

    public void setUrlContent(char[] urlContent) throws IOException {
        this.urlContent = getWebSiteContent(getUrlInfo());
    }

    public LocalDate getAdditionDate() {
        return additionDate;
    }

    public void setAdditionDate(LocalDate additionDate) {
        this.additionDate = additionDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WebPage{");
        sb.append("id=").append(id);
        sb.append(", urlInfo='").append(urlInfo).append('\'');
        sb.append(", urlContent=").append(Arrays.toString(urlContent));
        sb.append(", additionDate=").append(additionDate);
        sb.append('}');
        return sb.toString();
    }

    private synchronized char[] getWebSiteContent(String url) throws IOException {

        URL urlAdddres = new URL(url);

        URLConnection connection = urlAdddres.openConnection();
        InputStream stream = connection.getInputStream();

        StringBuilder builder = new StringBuilder();
        char[] webSiteContent;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
        } catch (MalformedURLException e){
            //dodaj tutaj logowanie na konsole
            throw new MalformedURLException("URL is malformed!!!");
        }
        webSiteContent = builder.toString().toCharArray();
        return webSiteContent;
    }
}


