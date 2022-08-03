package sqlformatterbat.common.config;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;

/**
 * DataSource管理用バンドルクラス. <br/>
 * SpringBatch標準の管理テーブルの使用を避けたので本クラスでコネクション管理
 *
 * @author Tomo
 */
@Configuration
@Getter
public class ApplicationPropBundle {

    private String indent;
    private boolean uppercase;
    private int linesBetweenQueries;
    private int maxColumnLength;
    private String params;

    ApplicationPropBundle() {
        ResourceBundle rb = ResourceBundle.getBundle("application", Locale.getDefault());
        String indentRepeatNum = Objects.nonNull(rb.getString("app.format.indent")) ? rb.getString("app.format.indent") : "2";
        this.indent = StringUtils.repeat(" ", Integer.parseInt(indentRepeatNum));
        this.uppercase = Objects.nonNull(rb.getString("app.format.uppercase")) ? Boolean.parseBoolean(rb.getString("app.format.uppercase")) : false;
        this.linesBetweenQueries = Objects.nonNull(rb.getString("app.format.linesBetweenQueries")) ? Integer.parseInt(rb.getString("app.format.linesBetweenQueries")) : 2;
        this.maxColumnLength = Objects.nonNull(rb.getString("app.format.maxColumnLength")) ? Integer.parseInt(rb.getString("app.format.maxColumnLength")) : 100;
        // this.params = rb.getString("app.format.params");
    }
}
