package sqlformatterbat.job0010;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.LineNumberReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.github.vertical_blank.sqlformatter.SqlFormatter;
import com.github.vertical_blank.sqlformatter.core.FormatConfig;
import com.github.vertical_blank.sqlformatter.languages.Dialect;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import sqlformatterbat.common.config.ApplicationPropBundle;

@Slf4j
@Component
@StepScope
public class SqlFormatterBatTasklet implements Tasklet {

    @Autowired
    ApplicationPropBundle prop;

    @Value("#{jobParameters[targetFile]}")
    String targetFile;
    @Value("#{jobParameters[inputFileEncode]}")
    String inputFileEncode;
    @Value("#{jobParameters[dialect]}")
    String dialect;
    Dialect dialectSelected;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
            throws Exception {
        // 初期処理
        setup();
        String indent = prop.getIndent();
        boolean uppercase = prop.isUppercase();
        int linesBetweenQueries = prop.getLinesBetweenQueries();
        int maxColumnLength = prop.getMaxColumnLength();
        log.info("[START] SQL Format");
        log.info("############## Setting Info ######################");
        log.info("targetFile:" + targetFile);
        log.info("inputFileEncode:" + inputFileEncode);
        log.info("dialectSelected:" + dialectSelected.toString());
        log.info("indent:[" + indent + "]");
        log.info("uppercase:" + uppercase);
        log.info("linesBetweenQueries:" + linesBetweenQueries);
        log.info("maxColumnLength:" + maxColumnLength);
        log.info("##################################################");

        String sql = Files.lines(Paths.get(targetFile), Charset.forName(inputFileEncode))
                .collect(Collectors.joining(System.getProperty("line.separator")));
        log.info("整形前クエリ：" + sql);
//        String sqlFormatted = SqlFormatter.of(dialectSelected).format(sql);
        String sqlFormatted = SqlFormatter.of(dialectSelected).format(sql,
                FormatConfig.builder().indent(prop.getIndent()) // Defaults to two spaces
                        .uppercase(prop.isUppercase()) // Defaults to false (not safe to use when SQL dialect has case-sensitive identifiers)
                        .linesBetweenQueries(prop.getLinesBetweenQueries()) // Defaults to 1
                        .maxColumnLength(prop.getMaxColumnLength()) // Defaults to 50
                        .build());
        log.info("整形後クエリ：" + sqlFormatted);
        BufferedWriter bw = Files.newBufferedWriter(Paths.get(targetFile + ".tmp"),
                Charset.forName(inputFileEncode), StandardOpenOption.CREATE);
        bw.write(sqlFormatted);
        bw.close();
        Files.copy(Paths.get(targetFile + ".tmp"), Paths.get(targetFile), StandardCopyOption.REPLACE_EXISTING);
        Files.delete(Paths.get(targetFile + ".tmp"));
        log.info("[END] SQL Format");
        return RepeatStatus.FINISHED;
    }

    /**
     * セットアップ処理.
     */
    private void setup() throws FileNotFoundException {
        if (StringUtils.isEmpty(targetFile)) {
            throw new FileNotFoundException("ファイルが見つかりませんでした・・");
        }
        if (StringUtils.isEmpty(inputFileEncode)) {
            // 文字コードが指定された場合、指定された文字コードを使用する
            inputFileEncode = "UTF-8";
        }
        if (Dialect.MySql.toString().equalsIgnoreCase(dialect)) {
            dialectSelected = Dialect.MySql;
        } else if (Dialect.Db2.name().equalsIgnoreCase(dialect)) {
            dialectSelected = Dialect.Db2;
        } else if (Dialect.MariaDb.name().equalsIgnoreCase(dialect)) {
            dialectSelected = Dialect.MariaDb;
        } else if (Dialect.N1ql.name().equalsIgnoreCase(dialect)) {
            dialectSelected = Dialect.N1ql;
        } else if (Dialect.PlSql.name().equalsIgnoreCase(dialect)) {
            dialectSelected = Dialect.PlSql;
        } else if (Dialect.PostgreSql.name().equalsIgnoreCase(dialect)) {
            dialectSelected = Dialect.PostgreSql;
        } else if (Dialect.Redshift.name().equalsIgnoreCase(dialect)) {
            dialectSelected = Dialect.Redshift;
        } else if (Dialect.SparkSql.name().equalsIgnoreCase(dialect)) {
            dialectSelected = Dialect.SparkSql;
        } else if (Dialect.StandardSql.name().equalsIgnoreCase(dialect)) {
            dialectSelected = Dialect.StandardSql;
        } else if (Dialect.TSql.name().equalsIgnoreCase(dialect)) {
            dialectSelected = Dialect.TSql;
        } else {
            // SQL方言設定：デフォルトはStandardSql
            dialectSelected = Dialect.StandardSql;
        }
    }
}
