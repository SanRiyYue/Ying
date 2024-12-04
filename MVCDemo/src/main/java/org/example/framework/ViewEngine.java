package org.example.framework;

import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.loader.Servlet5Loader;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import jakarta.servlet.ServletContext;

import java.io.IOException;
import java.io.Writer;

public class ViewEngine {
    private final PebbleEngine engine;
    public ViewEngine(ServletContext servletContext) {
        //定义一个ServletLoader用于加载模版
        var loader = new Servlet5Loader(servletContext);

        //模版编码
        loader.setCharset("UTF-8");

        //模版加载地址
        loader.setPrefix("WEB-INF/templates");

        //模版后缀
        loader.setSuffix("");

        //创建Pebble实例
        this.engine = new PebbleEngine.Builder().
                autoEscaping(true). //默认打开HTML字符转义，防止XSS攻击
                cacheActive(false). //禁用缓存使得每次修改模版可以立刻看到效果
                loader(loader).build();
    }

    public void render(ModelAndView mv, Writer writer) throws IOException {
        //查找模版
        PebbleTemplate template = this.engine.getTemplate(mv.view);
        //渲染
        template.evaluate(writer, mv.model);
    }

}
