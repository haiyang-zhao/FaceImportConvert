import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {

    @ExcelProperty("姓名*")
    private String name;
    @ExcelProperty("工号#")
    private String uid;
    @ExcelProperty("部门#")
    private String department;
    @ExcelProperty("职位#")
    private String job;
    @ExcelProperty("电话#")
    private String photo;
    @ExcelProperty("邮箱#")
    private String email;
    @ExcelProperty("签名#")
    private String signature;
    @ExcelProperty("备注#")
    private String remark;
    @ExcelProperty("唯一标识#")
    private String extraId;
    @ExcelProperty("卡号#")
    private String cardNo;

    @ExcelIgnore
    private List<String> photos = new ArrayList<>();
}