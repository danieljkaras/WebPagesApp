package com.daniel.model;

import org.jsoup.Jsoup;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@Entity

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

    public String downloadHtmlContent() throws IOException {
        String htmlContent = Jsoup.connect(this.url).get().html();
        this.content = htmlContent;
        return htmlContent;
    }
}


