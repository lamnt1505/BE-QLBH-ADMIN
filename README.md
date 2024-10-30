# Hướng dẫn tạo mới branch
- Clone dự án: Open dự án bằng Intellij. ***File -> New -> Open project from Version Control***
- Checkout nhánh mới theo tên họ của mình: ***git checkout -b ten_nhanh***
- Push nhánh mới lên remote server: ***git push --set-upstream origin ten_nhanh***
- Sau khi có nhánh của riêng mình thực hiện code, commit, push lên lên nhánh của mình.

# Lưu ý trước khi merge với nhánh dev
- B1: Thực hiện reabase từ nhánh dev. Sử dụng Intelij
- B2: Fix conflict ( Nếu có )
- B3: Thực hiện push lên nhánh của mình. ***git push -f***
- B4: Thực hiện checkout sang nhánh ***dev***
- B5: Thực hiện merge nhánh của mình vào dev: ***git merge ten_nhanh***
- B6: Thực hiện push nhánh dev: ***git push origin dev***

# Yêu cầu dự án!
Lập trình 1 website thương mại điện tử chuyên bán Thiết bị điện tử: Điện thoại, Máy tính, Đồng Hồ, Phụ Kiện, Tivi…( Tương tự thế giới di động, cellphones, Phong Vũ…)
- https://www.thegioididong.com/
- https://cellphones.com.vn/
- https://phongvu.vn/

# Yêu cầu cơ bản
- Chức năng đăng ký, đăng nhập, đăng xuất, phân quyền - **đã làm**
- Chức năng quản lý các danh mục: Loại sản phẩm, Sản phẩm - **đã làm**
- Màn hình chính: Liệt kê các sản phẩm nỗi bật, tìm kiếm sản phẩm, filter theo loại, giá, tính năng… - **thiếu tìm kiếm**
- Chức năng xem chi tiết sản phẩm - đã làm 
- Chức năng chọn sản phẩm để so sánh - đã làm 
- Chức năng quản lý giỏ hàng: Thêm hàng vào giỏ, xóa, sửa… đặt hàng - đã làm 
- Chức năng quản lý đơn hàng theo người dùng - đã làm
- Chức năng tra cứu đơn hàng đã được đặt - đã làm
- Chức năng đánh giá sản phẩm.- đang làm
- Chức năng áp dụng mã giảm giá - đang làm

# Yêu cầu nâng cao
- Đăng nhập bằng goolge, facebook…
- Chức năng cho khách hàng tự build PC -> Xuất báo giá
- Chức năng tích điểm thưởng khi mua hàng
- Chức năng thống kê: sản phẩm bán được, doanh thu theo từng tháng, quý, năm..
- Chức năng kiểm tra hàng còn hàng hay hết hàng tại chi nhánh bất kì.
- Đặt hàng sản phẩm chuẩn bị về nước: Ví dụ đặt gạch cho Iphone15.
- Chat với nhân viên
- Trả góp
- Dashboard cho admin

# Yêu cầu công nghệ
- BE: Java, Spring framework
- FE: JS, JQuery hoặc framewok ReactJS, Angular (Nếu dùng framework khuyến khích làm Angular theo định hướng của VNPT)
DB: MySQL