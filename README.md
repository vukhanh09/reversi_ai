Cờ Othello hay còn gọi là Reversi, hay Cờ Lật trong tiếng Việt, là một trò chơi của Đức ở trên bàn cờ và là môn thể thao trí tuệ dành cho hai người chơi.

Môi trường:
+ Reversi được cài đặt bằng ngôn ngữ Java
+ Ứng dụng có sử dụng SQL Server để thống kê, do đó nếu bạn muốn sử dụng chức năng này thì vui lòng mở lại comment tại dòng 207 class GamePanel.java
,cài đặt thêm JDBC để kết nối đến SQL và thay đổi tài khoản mật khẩu SQL tại "Database/MConnection.java"
-> file database đã có ở trong source code

Chế độ chơi:
+ AI vs AI
+ Human vs AI
+ Random vs AI(Human)

Các thuật toán sử dụng
+ Minimax, cắt tỉa alpha,beta
+ Các hàm heuristic

Ứng dụng có tham khảo của tác giả : arminkz
+ Sử dụng lại giao diện, các hàm phụ trợ, ngoài ta thì team đã phát triển, và chỉnh sửa lại nhiều các chức năng thuận tiện người chơi.
+ Code lại hàm minimax và cắt tỉa alpha,beta.
+ Nhóm có cải tiến thêm hàm heuristic đánh giá vị trí ô.
+ Link github tham khảo: https://github.com/arminkz/Reversi

