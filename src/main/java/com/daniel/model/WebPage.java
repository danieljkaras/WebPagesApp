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
    @Column(name = "url")
    private String url;

    @Column(name = "content", length = 10000000)
    private String content;

    public WebPage() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WebPage{");
        sb.append("id=").append(id);
        sb.append(", url='").append(url).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public synchronized String downloadContent() throws IOException {

        URL urlAdddres = new URL(this.url);

        URLConnection connection = urlAdddres.openConnection();
        InputStream stream = connection.getInputStream();

        StringBuilder builder = new StringBuilder();
        String webSiteContent;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
        } catch (MalformedURLException e) {
            throw new MalformedURLException("URL is malformed!!!");
        }
        webSiteContent = builder.toString();

        this.content = webSiteContent;
        return webSiteContent;
    }
}


