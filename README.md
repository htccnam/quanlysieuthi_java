Dưới đây là nội dung chi tiết cho file `README.md` của dự án **quanlysieuthi_java**, được tổng hợp dựa trên cấu trúc và mã nguồn của chính dự án.

---

# 🛒 Hệ thống Quản lý Siêu thị (Java Swing + MySQL)

[![Java Version](https://img.shields.io/badge/Java-21-blue.svg)](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

Đây là một ứng dụng quản lý siêu thị toàn diện được phát triển bằng ngôn ngữ **Java**, sử dụng **Swing** cho giao diện người dùng và **MySQL** làm hệ quản trị cơ sở dữ liệu. Dự án được xây dựng theo mô hình **MVC (Model-View-Controller)**, hướng đến việc cung cấp một giải pháp quản lý hiệu quả cho các cửa hàng bán lẻ với các chức năng nghiệp vụ cốt lõi.

> 🌟 Dự án là kết quả làm việc nhóm của 4 thành viên:
> 1. Hoàng Hải Nam (Captain)
> 2. Lê Tấn Khang
> 3. Vũ Hùng Hải
> 4. Nguyễn Việt Hoàng

---

## 📌 Tính năng chính

Hệ thống được thiết kế với đầy đủ các nghiệp vụ quản lý của một siêu thị, bao gồm:

-   **🔐 Đăng nhập**: Giao diện đăng nhập an toàn.
-   **📦 Quản lý Sản phẩm**: Cho phép thêm, sửa, xóa, tìm kiếm và xem danh sách sản phẩm với các thông tin chi tiết (mã SP, tên SP, loại hàng, nhà cung cấp, xuất xứ, số lượng, ngày sản xuất, hạn sử dụng, giá nhập/bán).
-   **🏷️ Quản lý Loại hàng & Nhà cung cấp**: Quản lý danh mục và nhà cung cấp.
-   **👥 Quản lý Khách hàng & Thành viên**: Quản lý thông tin cá nhân cùng với điểm tích lũy.
-   **🧾 Quản lý Đơn hàng**: Tạo và theo dõi các giao dịch bán hàng, bao gồm chi tiết sản phẩm, phương thức thanh toán, và tổng tiền.
-   **🎁 Quản lý Khuyến mãi**: Cập nhật và áp dụng các chương trình khuyến mãi.
-   **👨‍💼 Quản lý Nhân viên & Chức vụ**: Quản lý thông tin và phân quyền cho nhân viên.
-   **🔄 Đổi quà tích điểm**: Cho phép khách hàng đổi điểm thưởng lấy quà.
-   **🔍 Báo cáo & Tìm kiếm**: Hỗ trợ tìm kiếm nhanh chóng trên tất cả các danh mục.

## 🛠️ Công nghệ sử dụng

-   **Ngôn ngữ**: Java 21
-   **Giao diện người dùng (UI)**: Swing (JPanel, JTable, JDateChooser,...)
-   **Cơ sở dữ liệu**: MySQL
-   **Kết nối CSDL**: JDBC với MySQL Connector/J (phiên bản 8.0.30)
-   **Thư viện hỗ trợ**: JCalendar (cho việc chọn ngày tháng)
-   **Kiến trúc**: Mô hình MVC, phân tách rõ ràng các thành phần Model, View, Controller.

## 🚀 Hướng dẫn cài đặt và chạy dự án

### Yêu cầu hệ thống
-   **JDK**: Phiên bản 21 trở lên (hoặc tương thích)
-   **MySQL Server**: Đã cài đặt và chạy trên máy.
-   **IDE**: NetBeans, IntelliJ IDEA, Eclipse hoặc bất kỳ IDE nào hỗ trợ Java.
-   **Maven**: Dự án sử dụng Maven để quản lý dependencies.

### Các bước thực hiện

1.  **Clone dự án về máy:**
    ```bash
    git clone https://github.com/htccnam/quanlysieuthi_java.git
    ```

2.  **Tạo cơ sở dữ liệu:**
    -   Tạo một Database trên MySQL (ví dụ: `quanlysieuthi_db`).
    -   Chạy script SQL (hiện chưa có trong repository) để tạo các bảng cần thiết (sanpham, donhang, khachhang, ...). file được đính kèm database.sql

3.  **Cấu hình kết nối cơ sở dữ liệu:**
    -   Trong dự án, tìm file `DAO/DBConnection.java` hoặc file chứa thông tin kết nối.
    -   Cập nhật các thông số:
        -   `URL`: jdbc:mysql://localhost:3306/quanlysieuthi_db
        -   `USERNAME`: tên người dùng MySQL của bạn
        -   `PASSWORD`: mật khẩu tương ứng

4.  **Chạy ứng dụng:**
    -   Mở dự án bằng IDE.
    -   Đảm bảo Maven tải đầy đủ các dependencies (kiểm tra file `pom.xml`).
    -   Tìm và chạy file main (có thể là `Quanlysieuthi.java` hoặc một file View khởi tạo chính trong package `VIEW`).

5.  **Đăng nhập:**
    -   Ứng dụng sẽ hiển thị màn hình đăng nhập.
    -   Sử dụng tài khoản có sẵn trong database để truy cập.

## 📂 Cấu trúc thư mục dự án

```
quanlysieuthi_java/
├── src/
│   └── main/
│       └── java/
│           ├── CONTROLLER/      # Xử lý logic nghiệp vụ và tương tác giữa Model và View
│           │   ├── SanPhamController.java
│           │   ├── KhachHangController.java
│           │   └── ...
│           ├── DAO/             # Data Access Object - Thao tác trực tiếp với Database
│           │   ├── SanPhamDAO.java
│           │   ├── DonHangDAO.java
│           │   └── ...
│           ├── MODEL/           # Các lớp đối tượng (Entities) ánh xạ với bảng trong Database
│           │   ├── SanPham.java
│           │   ├── DonHang.java
│           │   ├── KhachHang.java
│           │   └── ...
│           └── VIEW/            # Giao diện người dùng (Swing components)
│               ├── SanPhamView.java
│               ├── KhachHangView.java
│               ├── LoginView.java
│               └── ...
├── pom.xml                      # Cấu hình Maven (dependencies, build)
└── README.md                    # Tài liệu dự án (bạn đang đọc!)
```

## 🤝 Đóng góp

Dự án được phát triển bởi nhóm sinh viên. Mọi đóng góp, báo cáo lỗi hoặc đề xuất cải thiện đều được chào đón.
-   **Fork** repository, tạo **branch** mới để phát triển tính năng, sau đó tạo **Pull Request**.
-   Hoặc tạo **Issue** để báo cáo lỗi hoặc thảo luận về các tính năng mới.

## 📜 Giấy phép

Dự án được phân phối dưới giấy phép **MIT**. Bạn có thể tự do sử dụng, sửa đổi và phân phối dự án này. Xem file `LICENSE` để biết thêm chi tiết.

## 📧 Liên hệ

Nếu bạn có bất kỳ câu hỏi nào về dự án, vui lòng liên hệ qua GitHub:
-   **Tác giả chính**: [htccnam](https://github.com/htccnam)
-   **Các thành viên khác**: [NorSaH77](https://github.com/NorSaH77), [LangKhon69](https://github.com/LangKhon69), [vuhunghai205](https://github.com/vuhunghai205)

---

**Lưu ý:** File `README.md` này được viết dựa trên cấu trúc hiện tại của repository. Đối với các hướng dẫn chi tiết nhất (đặc biệt là script tạo database và cấu hình kết nối cụ thể), vui lòng xem thêm trong mã nguồn của dự án hoặc liên hệ trực tiếp với nhóm phát triển.
