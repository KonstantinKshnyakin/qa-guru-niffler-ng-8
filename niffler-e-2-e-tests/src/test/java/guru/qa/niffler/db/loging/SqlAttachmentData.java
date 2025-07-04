package guru.qa.niffler.db.loging;

import io.qameta.allure.attachment.AttachmentData;
import lombok.Getter;

@Getter
public class SqlAttachmentData implements AttachmentData {

    private final String name;
    private final String sql;

    public SqlAttachmentData(String name, String sql) {
        this.name = name;
        this.sql = sql;
    }
}
