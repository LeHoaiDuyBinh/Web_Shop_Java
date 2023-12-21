CREATE DATABASE IF NOT EXISTS ptit_shop;

USE ptit_shop;

DROP TABLE IF EXISTS `Categories`;

CREATE TABLE Categories (
  category_id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  parent_category_id INT,
  FOREIGN KEY (`parent_category_id`) REFERENCES `Categories`(`category_id`)
);
DROP TABLE IF EXISTS `Products`;

CREATE TABLE Products (
  product_code VARCHAR(50) PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  description TEXT NOT NULL,
  price DECIMAL(10,3) NOT NULL,
  category_id INT NOT NULL,
  color ENUM('red', 'pink', 'yellow', 'green', 'blue', 'beige', 'white', 'black', 'brown', 'gray'),
  update_latest DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (`category_id`) REFERENCES `Categories`(`category_id`)
);
DROP TABLE IF EXISTS `ProductImages`;

CREATE TABLE ProductImages(
  product_code VARCHAR(50),
  ordinal_number VARCHAR(50),
  image VARCHAR(200) NOT NULL,
  PRIMARY KEY (product_code, ordinal_number),
  FOREIGN KEY (`product_code`) REFERENCES `Products`(`product_code`) ON DELETE CASCADE
);

DROP TABLE IF EXISTS `ProductSizes`;

CREATE TABLE ProductSizes(
  product_code VARCHAR(50),
  size ENUM('S', 'M', 'L', 'XL', 'XXL'), 
  quantity INT DEFAULT 0,
  PRIMARY KEY (product_code, size),
  FOREIGN KEY (`product_code`) REFERENCES `Products`(`product_code`) ON DELETE CASCADE,
  CHECK (quantity >= 0)
);
DROP TABLE IF EXISTS `Customers`;

CREATE TABLE Customers (
  email VARCHAR(100) PRIMARY KEY,
  password VARCHAR(100) NOT NULL,
  full_name NVARCHAR(150) NOT NULL,
  phone VARCHAR(20)
);

DROP TABLE IF EXISTS `Verify`;

CREATE TABLE Verify (
  email VARCHAR(100) PRIMARY KEY,
  token VARCHAR(255) UNIQUE,
  count INT,
  used TINYINT(1) DEFAULT 0,
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
DROP TABLE IF EXISTS `ShoppingCart`;

CREATE TABLE ShoppingCart (
  cart_code VARCHAR(150) PRIMARY KEY,
  email VARCHAR(100) NOT NULL,
  FOREIGN KEY (`email`) REFERENCES `Customers`(`email`) ON DELETE CASCADE
);
DROP TABLE IF EXISTS `CartItems`;

CREATE TABLE CartItems (
  cart_item_id INT PRIMARY KEY AUTO_INCREMENT,
  cart_code VARCHAR(150),
  product_code VARCHAR(50),
  quantity INT NOT NULL,
  size ENUM('S', 'M', 'L', 'XL', 'XXL'),
  total_price DECIMAL(10,3) NOT NULL,
  FOREIGN KEY (`cart_code`) REFERENCES `ShoppingCart`(`cart_code`) ON DELETE CASCADE,
  FOREIGN KEY (`product_code`) REFERENCES `Products`(`product_code`) ON DELETE CASCADE
);

DROP TABLE IF EXISTS `Orders`;

CREATE TABLE Orders (
  order_code VARCHAR(100) PRIMARY KEY,
  order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  state ENUM('pending', 'delivered','delivering', 'cancelled') DEFAULT 'pending',
  total_price DECIMAL(10,3) DEFAULT 0,
  email VARCHAR(100),
  address NVARCHAR(150) NOT NULL,
  FOREIGN KEY (`email`) REFERENCES `Customers`(`email`) ON DELETE CASCADE
);
DROP TABLE IF EXISTS `OrderItems`;

CREATE TABLE OrderItems (
  order_item_id INT PRIMARY KEY AUTO_INCREMENT,
  order_code VARCHAR(100),
  product_code VARCHAR(50),
  quantity INT NOT NULL,
  size ENUM('S', 'M', 'L', 'XL', 'XXL'),
  total_price DECIMAL(10,3) NOT NULL,
  FOREIGN KEY (`order_code`) REFERENCES `Orders`(`order_code`) ON DELETE CASCADE,
  FOREIGN KEY (`product_code`) REFERENCES `Products`(`product_code`) ON DELETE CASCADE
);

DROP TABLE IF EXISTS `Payment`;

CREATE TABLE Payment (
  payment_code VARCHAR(50) PRIMARY KEY,
  payment_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  order_code VARCHAR(100),
  type ENUM('cash', 'bank_transfer'),
  FOREIGN KEY (`order_code`) REFERENCES `Orders`(`order_code`) ON DELETE CASCADE
); 

DROP TABLE IF EXISTS `OrdersHistory`;

CREATE TABLE OrdersHistory (
  order_code VARCHAR(100) PRIMARY KEY,
  order_date DATETIME,
  payment_code VARCHAR(50),
  payment_date DATETIME,
  payment_type ENUM('cash', 'bank_transfer'),
  state ENUM('pending', 'delivered','delivering', 'cancelled'),
  email VARCHAR(100),
  address NVARCHAR(150),
  phone VARCHAR(20),
  total_price DECIMAL(10,3)
); 

DROP TABLE IF EXISTS `OrdersHistoryItems`;

CREATE TABLE OrdersHistoryItems (
  history_detail_id INT PRIMARY KEY AUTO_INCREMENT,
  order_code VARCHAR(100),
  product_code VARCHAR(50),
  quantity INT,
  size ENUM('S', 'M', 'L', 'XL', 'XXL'),
  total_price DECIMAL(10,3),
  FOREIGN KEY (`order_code`) REFERENCES `OrdersHistory`(`order_code`)
); 
DROP TABLE IF EXISTS `AdminAccounts`;

CREATE TABLE AdminAccounts (
  username VARCHAR(50) NOT NULL PRIMARY KEY,
  password VARCHAR(255) NOT NULL, 
  role ENUM('admin', 'manager', 'staff') NOT NULL,
  status_expire TINYINT(1) DEFAULT 0
);

-- SP LOAD PRODUCT
DROP PROCEDURE IF EXISTS `GetProducts`;

DELIMITER //
CREATE PROCEDURE GetProducts()
BEGIN
  SELECT P.*, C.name AS 'category_name' FROM Products AS P, Categories AS C 
  WHERE P.category_id = C.category_id;
END;
//
DELIMITER ;

-- CALL GetProducts();
DROP PROCEDURE IF EXISTS `GetSizesProduct`;

DELIMITER //
CREATE PROCEDURE GetSizesProduct(IN product_code VARCHAR(50))
BEGIN
  SELECT PS.* FROM Products AS P, ProductSizes AS PS
  WHERE P.product_code = PS.product_code AND P.product_code = product_code;
END;
//
DELIMITER ;

-- CALL GetSizesProduct('P001');
DROP PROCEDURE IF EXISTS `GetImagesProduct`;

DELIMITER //
CREATE PROCEDURE GetImagesProduct(IN product_code VARCHAR(50))
BEGIN
  SELECT PI.* FROM Products AS P, ProductImages AS PI 
  WHERE P.product_code = PI.product_code AND P.product_code = product_code;
END;
//
DELIMITER ;

-- CALL GetImagesProduct('P001');

DROP PROCEDURE IF EXISTS `ResetStatus`;

DELIMITER //
CREATE PROCEDURE ResetStatus(IN username VARCHAR(50))
BEGIN
  UPDATE AdminAccounts AS AA SET AA.status_expire = 0 WHERE AA.username = username;
END;
//
DELIMITER ;

-- Tạo người dùng chỉ đọc
CREATE USER 'staff'@'%' IDENTIFIED BY 'day_la_staff_ptithcm';

-- Cấp quyền chỉ đọc cho người dùng trên cơ sở dữ liệu cụ thể (thay đổi tên database_name)
GRANT SELECT ON ptit_shop.* TO 'staff'@'%';


-- Tạo manager có thể CRUD mọi bảng trừ Admins
CREATE USER 'manager'@'%' IDENTIFIED BY 'day_la_manager_ptithcm';
GRANT SELECT, INSERT, UPDATE, DELETE ON ptit_shop.Categories TO 'manager'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON ptit_shop.Products TO 'manager'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON ptit_shop.ProductImages TO 'manager'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON ptit_shop.ProductSizes TO 'manager'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON ptit_shop.Customers TO 'manager'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON ptit_shop.Verify TO 'manager'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON ptit_shop.ShoppingCart TO 'manager'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON ptit_shop.CartItems TO 'manager'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON ptit_shop.Orders TO 'manager'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON ptit_shop.OrderItems TO 'manager'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON ptit_shop.Payment TO 'manager'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON ptit_shop.OrdersHistory TO 'manager'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON ptit_shop.OrdersHistoryItems TO 'manager'@'%';
GRANT SELECT ON ptit_shop.AdminAccounts TO 'manager'@'%';

GRANT EXECUTE ON PROCEDURE ptit_shop.GetProducts TO 'manager'@'%';
GRANT EXECUTE ON PROCEDURE ptit_shop.GetProducts TO 'staff'@'%';
GRANT EXECUTE ON PROCEDURE ptit_shop.GetSizesProduct TO 'manager'@'%';
GRANT EXECUTE ON PROCEDURE ptit_shop.GetSizesProduct TO 'staff'@'%';
GRANT EXECUTE ON PROCEDURE ptit_shop.GetImagesProduct TO 'manager'@'%';
GRANT EXECUTE ON PROCEDURE ptit_shop.GetImagesProduct TO 'staff'@'%';
GRANT EXECUTE ON PROCEDURE ptit_shop.ResetStatus TO 'manager'@'%';
GRANT EXECUTE ON PROCEDURE ptit_shop.ResetStatus TO 'staff'@'%';

CREATE USER 'customer'@'%' IDENTIFIED BY 'day_la_customer_ptithcm';
GRANT SELECT, INSERT, UPDATE, DELETE ON ptit_shop.CartItems TO 'customer'@'%';
GRANT SELECT ON ptit_shop.Categories TO 'customer'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON ptit_shop.Customers TO 'customer'@'%';
GRANT SELECT, INSERT ON ptit_shop.ShoppingCart TO 'customer'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON ptit_shop.Verify TO 'customer'@'%';
GRANT SELECT, INSERT ON ptit_shop.Orders TO 'customer'@'%';
GRANT SELECT, INSERT ON ptit_shop.OrderItems TO 'customer'@'%';
GRANT SELECT, INSERT ON ptit_shop.Payment TO 'customer'@'%';
GRANT SELECT, INSERT ON ptit_shop.OrdersHistory TO 'customer'@'%';
GRANT SELECT, INSERT ON ptit_shop.OrdersHistoryItems TO 'customer'@'%';
GRANT SELECT ON ptit_shop.Products TO 'customer'@'%';
GRANT SELECT ON ptit_shop.ProductImages TO 'customer'@'%';
GRANT SELECT, UPDATE ON ptit_shop.ProductSizes TO 'customer'@'%';

GRANT EXECUTE ON PROCEDURE ptit_shop.GetProducts TO 'customer'@'%';
GRANT EXECUTE ON PROCEDURE ptit_shop.GetSizesProduct TO 'customer'@'%';
GRANT EXECUTE ON PROCEDURE ptit_shop.GetImagesProduct TO 'customer'@'%';

-- Tạo user admin full quyền
CREATE USER 'admin'@'%' IDENTIFIED BY 'day_la_admin_ptithcm';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON ptit_shop.* TO 'admin'@'%';
-- FLUSH PRIVILEGES;

INSERT INTO AdminAccounts (username, password, role) VALUES('moros', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'admin');
INSERT INTO AdminAccounts (username, password, role) VALUES('teo', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'staff');
INSERT INTO AdminAccounts (username, password, role) VALUES('dat', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'manager');

INSERT INTO `Categories`(`name`) VALUES ('Áo nam');
INSERT INTO `Categories`(`name`) VALUES ('Quần nam');
INSERT INTO `Categories`(`name`) VALUES ('Áo nữ');
INSERT INTO `Categories`(`name`) VALUES ('Quần nữ');

INSERT INTO `Categories`(`name`, `parent_category_id`) VALUES ('Áo sơ mi nam', 1);
INSERT INTO `Categories`(`name`, `parent_category_id`) VALUES ('Áo thun nam', 1);
INSERT INTO `Categories`(`name`, `parent_category_id`) VALUES ('Áo khoác nam', 1);
INSERT INTO `Categories`(`name`, `parent_category_id`) VALUES ('Áo len nam', 1);
INSERT INTO `Categories`(`name`, `parent_category_id`) VALUES ('Quần tây nam', 2);
INSERT INTO `Categories`(`name`, `parent_category_id`) VALUES ('Quần short nam', 2);

INSERT INTO `Categories`(`name`, `parent_category_id`) VALUES ('Áo sơ mi nữ', 3);
INSERT INTO `Categories`(`name`, `parent_category_id`) VALUES ('Áo thun nữ', 3);
INSERT INTO `Categories`(`name`, `parent_category_id`) VALUES ('Áo khoác nữ', 3);
INSERT INTO `Categories`(`name`, `parent_category_id`) VALUES ('Áo len nữ', 3);
INSERT INTO `Categories`(`name`, `parent_category_id`) VALUES ('Quần ống loe nữ', 4);
INSERT INTO `Categories`(`name`, `parent_category_id`) VALUES ('Quần short nữ', 4);

INSERT INTO `Products` (`product_code`, `name`, `description`, `price`, `category_id`, `color`, `update_latest`) VALUES
('SP1701933882', 'ÁO SƠ MI ĐÍNH HOA', 'Thời tiết sang Thu rất thích hợp để diện những key items sơ mi dài tay. Đến với thiết kế sơ mi lần này, IVY moda giữ nguyên dáng cơ bản, được cách điệu thêm chi tiết đính hoa ở cổ áo và xếp ly ở cổ tay. Áo có khuy cài nhỏ đồng màu. Chất liệu là lụa trơn cao cấp, mềm  mại, tạo cảm giác mặc thoải mái. \r\n\r\nMôt chiếc áo sơ mi với gam màu tươi tắn sẽ hoàn thành bất cứ outfit nào của nàng. Thiết kế cơ bản nhưng không hề nhàm chán luôn sẵn sàng để tôn lên vẻ đẹp của người mặc.\r\n\r\nThông tin mẫu:\r\n\r\nChiều cao: 167 cm\r\n\r\nCân nặng: 50 kg\r\n\r\nSố đo 3 vòng: 83-65-93 cm\r\n\r\nMẫu mặc size M\r\n\r\nLưu ý: Màu sắc sản phẩm thực tế sẽ có sự chênh lệch nhỏ so với ảnh do điều kiện ánh sáng khi chụp và màu sắc hiển thị qua màn hình máy tính/ điện thoại.', 100.00, 11, 'brown', '2023-12-07 14:24:47'),
('SP1701934515', 'ÁO SƠ MI NAM KẺ CARO', 'Áo sơ mi nam cổ đức, tay dài có khuy ở gấu. 2 túi có nắp (cài khuy) trước ngực. Họa tiết kẻ caro, phối màu ở cổ và nắp túi làm điểm nhấn. Cài bằng hàng khuy phía trước.\r\n\r\nMàu sắc: Kẻ Xanh Cổ Vịt Đậm - Kẻ Nude\r\n\r\nMẫu mặc size L:\r\n\r\nChiều cao: 1m85\r\nCân nặng: 75kg\r\nSố đo 3 vòng: 100-78-95 cm', 100.00, 5, 'blue', '2023-12-07 14:35:20'),
('SP1701934808', 'QUẦN ÂU NAM ĐẸP DÁNG SLIM', 'Quần Âu Kẻ caro có 2 túi chéo phía trước, 2 túi viền có 1 khuy phía sau. Đai quần có đỉa. Cài phía trước bằng khóa kéo và khuy.\r\n\r\nForm dáng Slim kết hợp kẻ caro tạo phong cách trẻ trung. Kiểu dáng hoàn hảo dành cho các chàng trai văn phòng, công sở. Bạn có thể thoải mái mặc từ những ngày đi làm bình thường hay những buổi họp hành cần sự chỉnh chu. Chất vải mềm mịn cùng lót trong sắc nét, tạo cảm giác thoải mái khi di chuyển và làm việc. \r\n\r\nKết hợp với áo sơ mi và vest cùng set tạo nên 1 bộ trang phục lịch lãm, sang trọng.\r\n\r\nMàu sắc: Họa tiết Chì - Họa tiết Xanh Dương Đậm - Họa tiết Nâu Đen - Họa tiết Xanh Tím Than', 100.00, 9, 'black', '2023-12-07 14:40:14'),
('SP1701935173', 'QUẦN ỐNG LOE ĐÍNH KHUY', '- Quần dáng ống loe, chiều dài qua mắt cá chân. Thiết kế quần cạp cao, bản to và có khóa kéo ẩn.\r\n\r\n- Thân trên quần dáng ôm body giúp tôn vòng ba, tạo hiệu ứng chân dài cực tốt. Thân dưới quần ống loe sang trọng, thời thượng. Quần tạo điểm nhấn qua các chi tiết khuy nổi bật ở cạp và phần thân trên. Các khuy sắp xếp đồng đều, khi sơ vin sẽ rất đẹp. \r\n\r\n- Nàng mix cùng áo sơ mi, áo thun là hoàn thiện outfit đi làm, đi học hay đi chơi.\r\n\r\nThông tin mẫu:\r\n\r\nChiều cao: 167 cm\r\n\r\nCân nặng: 50 kg\r\n\r\nSố đo 3 vòng: 83-65-93 cm\r\n\r\nMẫu mặc size M\r\n\r\nLưu ý: Màu sắc sản phẩm thực tế sẽ có sự chênh lệch nhỏ so với ảnh do điều kiện ánh sáng khi chụp và màu sắc hiển thị qua màn hình máy tính/ điện thoại.', 200.00, 15, 'black', '2023-12-07 14:46:19'),
('SP1701935516', 'QUẦN LỬNG KHAKI', 'Quần lửng khaki cạp chun co giãn có dây kéo rút. 2 túi chéo và 2 túi vuông có nắp phía sau. Thiết kế ấn tượng nhưng không kém phần ấn tượng cho nam giới diện mạo cuốn hút. Màu sắc cơ bản, độ dài ngang gối, có thể linh hoạt phối hợp với nhiều trang phục khác nhau.\r\n\r\nMàu sắc: Ghi Khói - Xanh Lơ\r\n\r\nMẫu mặc size L:\r\n\r\nChiều cao: 1m85\r\nCân nặng: 75kg\r\nSố đo 3 vòng: 100-78-95 cm', 200.00, 10, 'blue', '2023-12-07 14:52:01'),
('SP1701935684', 'QUẦN SOOC ORGANZA DẬP LY', '- Quần sooc cạp cao, dáng suông, ống đứng, thiết kế dập ly thân trước. \r\n\r\n- Thiết kế sử dụng chất liệu Organza cao cấp, đứng form, dày dặn, có độ bóng nhẹ\r\n\r\n- Quần có 2 túi 2 bên rất tiện lợi\r\n\r\n- Có thể mix quần cùng áo sơ mi, áo kiểu có chất liệu tương đồng, phù hợp diện khi đi chơi, đi tiệc.\r\n\r\nThông tin mẫu:\r\n\r\nChiều cao: 163 cm\r\n\r\nCân nặng: 47 kg\r\n\r\nSố đo 3 vòng: 83-61-90 cm\r\n\r\nMẫu mặc size S Lưu ý: Màu sắc sản phẩm thực tế sẽ có sự chênh lệch nhỏ so với ảnh do điều kiện ánh sáng khi chụp và màu sắc hiển thị qua màn hình máy tính/ điện thoại.', 200.00, 16, 'blue', '2023-12-07 14:54:49'),
('SP1701936101', 'ÁO THUN NAM TAY DÀI', 'Áo thun nam cổ đức có 3 khuy cài, tay dài. Dáng áo Regular. \r\n\r\nChất liệu vải thun cá sấu dày dặn thấm hút mồ hôi tốt giúp bạn thoải mái vận động. Mix cùng quần jean, short...giày thể thao để có set đồ năng động.\r\n\r\nMàu sắc: Xanh Cổ Vịt Nhạt - Đỏ Ruby\r\n\r\nMẫu mặc size L:\r\n\r\nChiều cao: 1m83\r\nCân nặng: 74kg', 300.00, 6, 'blue', '2023-12-07 15:01:46'),
('SP1701936210', 'ÁO THUN NỮ TAY NGẮN', 'Áo polo thời trang nữ linh hoạt và thoải mái. Thiết kế cổ đức có khuy cài, gấu tay bo nhẹ, trước ngực thêu họa tiết nổi bật.\r\n\r\nÁo được làm từ chất liệu thun co giãn nhẹ, thoáng khí với đường may chắc chắn, tỉ mỉ tạo nên vẻ bền bỉ và chất lượng.\r\n\r\nDễ dàng kết hợp với nhiều kiểu quần như jean, chino hoặc chân váy, tạo nên phong cách thời trang xu hướng. \r\n\r\nThông tin mẫu:\r\n\r\nChiều cao: 167 cm\r\n\r\nCân nặng: 50 kg\r\n\r\nSố đo 3 vòng: 83-65-93 cm\r\n\r\nMẫu mặc size M\r\n\r\nLưu ý: Màu sắc sản phẩm thực tế sẽ có sự chênh lệch nhỏ so với ảnh do điều kiện ánh sáng khi chụp và màu sắc hiển thị qua màn hình máy tính/ điện thoại.', 300.00, 12, 'white', '2023-12-07 15:03:35'),
('SP1701936580', 'ÁO KHOÁC NAM DÁNG LỬNG', 'Áo khoác nam cổ đức không chân. Tay dài. Có 2 túi chéo 2 bên. Cài bằng hàng khuy phía trước. 2 túi bên trong. Vải dạ bên trong có lớp lót khác màu. \r\nNgoài việc phối với nhiều đồ, áo khoác này bạn có thể mặc 2 mặt tạo thành 2 áo khác nhau. Vừa tiện dụng vừa hợp thời trang\r\n\r\nMàu sắc: Nâu cà phê - Xanh Cổ Vịt\r\n\r\nMẫu mặc size L:\r\n\r\nChiều cao: 1m85\r\nCân nặng: 75kg\r\nSố đo 3 vòng: 100-78-95 cm', 400.00, 7, 'blue', '2023-12-07 15:09:46'),
('SP1701936773', 'ÁO KHOÁC TWILL DÁNG LỬNG', 'Áo khoác dài tay dáng ngắn cổ tròn, khuy cài kim loại gam vàng lấp lánh tạo độ ấn tượng cho chiếc áo màu ghi xám đẹp mắt, kèm theo đó là 2 chiếc túi nhỏ trước ngực tạo kiểu. .  \r\n\r\nChất liệu Twill cao cấp và mềm mại, bên trong áo và chân váy đều có lớp lụa lót cùng màu. Thiết kế nổi bật, ấn tượng và bắt mắt. \r\n\r\nThông tin mẫu:\r\n\r\nChiều cao: 167 cm\r\n\r\nCân nặng: 50 kg\r\n\r\nSố đo 3 vòng: 83-65-93 cm\r\n\r\nMẫu mặc size M\r\n\r\nLưu ý: Màu sắc sản phẩm thực tế sẽ có sự chênh lệch nhỏ so với ảnh do điều kiện ánh sáng khi chụp và màu sắc hiển thị qua màn hình máy tính/ điện thoại.', 500.00, 13, 'gray', '2023-12-07 15:12:59'),
('SP1701937678', 'ÁO THUN ĐÍNH HOA', 'Áo thun cổ tròn, tay ngắn, dáng suông cơ bản. \r\n\r\nÁo được thiết kế hai màu tương phản nổi bật với những đường viền sắc nét. Thân trước áo tạo điểm nhấn qua chi tiết đính hoa độc đáo, mới lạ.\r\n\r\nÁo có thể mix cùng nhiều kiểu quần, chân váy khác nhau phù hợp với phong cách thời trang riêng mỗi người. \r\n\r\nThông tin mẫu:\r\n\r\nChiều cao: 167 cm\r\n\r\nCân nặng: 50 kg\r\n\r\nSố đo 3 vòng: 83-65-93 cm\r\n\r\nMẫu mặc size M Lưu ý: Màu sắc sản phẩm thực tế sẽ có sự chênh lệch nhỏ so với ảnh do điều kiện ánh sáng khi chụp và màu sắc hiển thị qua màn hình máy tính/ điện thoại.', 100.00, 12, 'black', '2023-12-07 15:28:04'),
('SP1701946810', 'ÁO SƠ MI LỤA CỔ KIỂU', 'Thêm một thiết kế sơ mi thời thượng vào tủ đồ của quý cô công sở hiện đại. Thiết kế áo sơ mi dáng cơ bản, được cách điệu xếp chồng phần cổ áo và xếp ly ở cổ tay. Áo có khuy cài nhỏ đồng màu. Chất liệu là lụa trơn cao cấp, mềm  mại, tạo cảm giác mặc thoải mái. \r\n\r\nMôt chiếc áo sơ mi với gam màu tươi tắn sẽ hoàn thành bất cứ outfit nào của nàng. Thiết kế cơ bản nhưng không hề nhàm chán luôn sẵn sàng để tôn lên vẻ đẹp của người mặc.\r\n\r\nThông tin mẫu:\r\n\r\nChiều cao: 163 cm\r\n\r\nCân nặng: 47 kg\r\n\r\nSố đo 3 vòng: 83-61-90 cm\r\n\r\nMẫu mặc size S Lưu ý: Màu sắc sản phẩm thực tế sẽ có sự chênh lệch nhỏ so với ảnh do điều kiện ánh sáng khi chụp và màu sắc hiển thị qua màn hình máy tính/ điện thoại.', 450.00, 11, 'green', '2023-12-07 18:00:16'),
('SP1701947140', 'ÁO THUN BASIC CỔ TRÒN', 'Áo thun basic Cổ tròn, cộc tay, dáng suông trơn. Kiểu dáng đơn giản phù hợp với thời trang dạo phố.\r\n\r\nChất thun mang đặc tính Thoáng mát, thấm hút mồ hôi tốt, co giãn lớn, màu sắc đẹp.\r\n\r\nMàu sắc: Nhiều màu\r\n\r\nThông tin mẫu:\r\n\r\nChiều cao: 163 cm\r\n\r\nCân nặng: 47 kg\r\n\r\nSố đo 3 vòng: 83-61-90 cm\r\n\r\nMẫu mặc size S Lưu ý: Màu sắc sản phẩm thực tế sẽ có sự chênh lệch nhỏ so với ảnh do điều kiện ánh sáng khi chụp và màu sắc hiển thị qua màn hình máy tính/ điện thoại.', 150.00, 12, 'blue', '2023-12-07 18:05:45'),
('SP1701947313', 'ÁO THUN TRƠN CỔ V', 'Áo thun cổ V, cộc tay, form suông basic phù hợp với mọi vóc dáng.\r\n\r\nSản phẩm được tạo ra từ chất liệu Thun cao cấp, với những tính năng vượt trội như thấm hút mồ hôi tốt và có độ co dãn giúp người mặc vô cùng thoải mái. Hơn hết có thể dễ dàng mix&amp;match được với nhiều kiểu quần khác nhau. Đấy chính là lý do để phái nữ nên có ít nhất một chiếc áo thun trong tủ đồ của bạn.\r\n\r\nMàu sắc: Trắng - Đen - Xanh Tím Than - Bạc - Cam - Hồng San Hô\r\n\r\nThông tin mẫu:\r\n\r\nChiều cao: 163 cm\r\n\r\nCân nặng: 47 kg\r\n\r\nSố đo 3 vòng: 83-61-90 cm\r\n\r\nMẫu mặc size S Lưu ý: Màu sắc sản phẩm thực tế sẽ có sự chênh lệch nhỏ so với ảnh do điều kiện ánh sáng khi chụp và màu sắc hiển thị qua màn hình máy tính/ điện thoại.', 150.00, 12, 'white', '2023-12-07 18:08:38'),
('SP1701947497', 'ÁO THUN TRƠN CỔ ĐỨC KHUY NGỌC TRAI', 'Áo thun cổ đức, cài bằng hàng khuy ngọc trai phía trước. Kiểu dáng, màu sắc basic, dáng suông cơ bản.\r\n\r\nChất liệu thun mang đặc tính co dãn, thấm hút mồ hôi vượt trội mang đến vẻ đẹp trẻ trung, năng động cho người mặc. Sản phẩm phù hợp với thời trang dạo phố, đi chơi, đi làm...\r\n\r\nMàu sắc: Trắng - Xanh Tím Than - Ghi Khói - Hồng san hô - Đen - Xanh Tím Than\r\n\r\nThông tin mẫu:\r\n\r\nChiều cao: 163 cm\r\n\r\nCân nặng: 47 kg\r\n\r\nSố đo 3 vòng: 83-61-90 cm\r\n\r\nMẫu mặc size S Lưu ý: Màu sắc sản phẩm thực tế sẽ có sự chênh lệch nhỏ so với ảnh do điều kiện ánh sáng khi chụp và màu sắc hiển thị qua màn hình máy tính/ điện thoại.', 150.00, 12, 'brown', '2023-12-07 18:11:42'),
('SP1701947692', 'ÁO THUN SPARKLE', 'Áo thun cổ V, tay ngắn gấu lật. Dáng áo xuông cơ bản. Mặt trước in và đính hạt kim sa hàng chữ. \r\n\r\nChất liệu cotton có 5% độ co giãn giúp thiết kế mềm mại, mát nhẹ trên bề mặt vải. Mix cùng quần Jean, chân váy, zuýp dài...bạn sẽ có những set đồ thời trang, cá tính, lịch sự.\r\n\r\nMàu sắc: Xanh Lơ - Đen\r\n\r\nMẫu mặc size S:\r\n\r\nChiều cao: 1m69\r\n\r\nCân nặng: 48kg\r\n\r\nSố đo: 80-60-90cm', 150.00, 12, 'black', '2023-12-07 18:14:58');

INSERT INTO `ProductSizes` (`product_code`, `size`, `quantity`) VALUES
('SP1701933882', 'S', 10),
('SP1701933882', 'M', 11),
('SP1701933882', 'L', 12),
('SP1701933882', 'XL', 13),
('SP1701933882', 'XXL', 14),
('SP1701934515', 'S', 21),
('SP1701934515', 'M', 22),
('SP1701934515', 'L', 23),
('SP1701934515', 'XL', 24),
('SP1701934515', 'XXL', 25),
('SP1701934808', 'S', 11),
('SP1701934808', 'M', 12),
('SP1701934808', 'L', 13),
('SP1701934808', 'XL', 14),
('SP1701934808', 'XXL', 15),
('SP1701935173', 'S', 8),
('SP1701935173', 'M', 9),
('SP1701935173', 'L', 10),
('SP1701935173', 'XL', 11),
('SP1701935173', 'XXL', 12),
('SP1701935516', 'S', 10),
('SP1701935516', 'M', 12),
('SP1701935516', 'L', 12),
('SP1701935516', 'XL', 12),
('SP1701935516', 'XXL', 13),
('SP1701935684', 'S', 88),
('SP1701935684', 'M', 99),
('SP1701935684', 'L', 10),
('SP1701935684', 'XL', 54),
('SP1701935684', 'XXL', 24),
('SP1701936101', 'S', 8),
('SP1701936101', 'M', 8),
('SP1701936101', 'L', 8),
('SP1701936101', 'XL', 8),
('SP1701936101', 'XXL', 8),
('SP1701936210', 'S', 9),
('SP1701936210', 'M', 9),
('SP1701936210', 'L', 9),
('SP1701936210', 'XL', 9),
('SP1701936210', 'XXL', 9),
('SP1701936580', 'S', 7),
('SP1701936580', 'M', 7),
('SP1701936580', 'L', 7),
('SP1701936580', 'XL', 7),
('SP1701936580', 'XXL', 77),
('SP1701936773', 'S', 5),
('SP1701936773', 'M', 5),
('SP1701936773', 'L', 5),
('SP1701936773', 'XL', 5),
('SP1701936773', 'XXL', 5),
('SP1701937678', 'S', 6),
('SP1701937678', 'M', 6),
('SP1701937678', 'L', 6),
('SP1701937678', 'XL', 6),
('SP1701937678', 'XXL', 6),
('SP1701946810', 'S', 6),
('SP1701946810', 'M', 6),
('SP1701946810', 'L', 6),
('SP1701946810', 'XL', 6),
('SP1701946810', 'XXL', 6),
('SP1701947140', 'S', 89),
('SP1701947140', 'M', 89),
('SP1701947140', 'L', 89),
('SP1701947140', 'XL', 89),
('SP1701947140', 'XXL', 90),
('SP1701947313', 'S', 78),
('SP1701947313', 'M', 78),
('SP1701947313', 'L', 7),
('SP1701947313', 'XL', 87),
('SP1701947313', 'XXL', 88),
('SP1701947497', 'S', 11),
('SP1701947497', 'M', 12),
('SP1701947497', 'L', 13),
('SP1701947497', 'XL', 14),
('SP1701947497', 'XXL', 15),
('SP1701947692', 'S', 58),
('SP1701947692', 'M', 58),
('SP1701947692', 'L', 58),
('SP1701947692', 'XL', 58),
('SP1701947692', 'XXL', 58);

INSERT INTO `ProductImages` (`product_code`, `ordinal_number`, `image`) VALUES
('SP1701933882', 'first', './public/products/2023/12/07/img1701933883.jpeg'),
('SP1701933882', 'fourth', './public/products/2023/12/07/img1701933886.jpeg'),
('SP1701933882', 'second', './public/products/2023/12/07/img1701933884.jpeg'),
('SP1701933882', 'third', './public/products/2023/12/07/img1701933885.jpeg'),
('SP1701934515', 'first', './public/products/2023/12/07/img1701934516.jpeg'),
('SP1701934515', 'fourth', './public/products/2023/12/07/img1701934519.jpeg'),
('SP1701934515', 'second', './public/products/2023/12/07/img1701934517.jpeg'),
('SP1701934515', 'third', './public/products/2023/12/07/img1701934518.jpeg'),
('SP1701934808', 'first', './public/products/2023/12/07/img1701934809.jpeg'),
('SP1701934808', 'fourth', './public/products/2023/12/07/img1701934813.jpeg'),
('SP1701934808', 'second', './public/products/2023/12/07/img1701934810.jpeg'),
('SP1701934808', 'third', './public/products/2023/12/07/img1701934812.jpeg'),
('SP1701935173', 'first', './public/products/2023/12/07/img1701935174.jpeg'),
('SP1701935173', 'fourth', './public/products/2023/12/07/img1701935177.jpeg'),
('SP1701935173', 'second', './public/products/2023/12/07/img1701935175.jpeg'),
('SP1701935173', 'third', './public/products/2023/12/07/img1701935176.jpeg'),
('SP1701935516', 'first', './public/products/2023/12/07/img1701935517.jpeg'),
('SP1701935516', 'fourth', './public/products/2023/12/07/img1701935520.jpeg'),
('SP1701935516', 'second', './public/products/2023/12/07/img1701935518.jpeg'),
('SP1701935516', 'third', './public/products/2023/12/07/img1701935519.jpeg'),
('SP1701935684', 'first', './public/products/2023/12/07/img1701935685.jpeg'),
('SP1701935684', 'fourth', './public/products/2023/12/07/img1701935688.jpeg'),
('SP1701935684', 'second', './public/products/2023/12/07/img1701935686.jpeg'),
('SP1701935684', 'third', './public/products/2023/12/07/img1701935687.jpeg'),
('SP1701936101', 'first', './public/products/2023/12/07/img1701936102.jpeg'),
('SP1701936101', 'fourth', './public/products/2023/12/07/img1701936105.jpeg'),
('SP1701936101', 'second', './public/products/2023/12/07/img1701936103.jpeg'),
('SP1701936101', 'third', './public/products/2023/12/07/img1701936104.jpeg'),
('SP1701936210', 'first', './public/products/2023/12/07/img1701936211.jpeg'),
('SP1701936210', 'fourth', './public/products/2023/12/07/img1701936214.jpeg'),
('SP1701936210', 'second', './public/products/2023/12/07/img1701936212.jpeg'),
('SP1701936210', 'third', './public/products/2023/12/07/img1701936213.jpeg'),
('SP1701936580', 'first', './public/products/2023/12/07/img1701936581.jpeg'),
('SP1701936580', 'fourth', './public/products/2023/12/07/img1701936585.jpeg'),
('SP1701936580', 'second', './public/products/2023/12/07/img1701936582.jpeg'),
('SP1701936580', 'third', './public/products/2023/12/07/img1701936584.jpeg'),
('SP1701936773', 'first', './public/products/2023/12/07/img1701936774.jpeg'),
('SP1701936773', 'fourth', './public/products/2023/12/07/img1701936777.jpeg'),
('SP1701936773', 'second', './public/products/2023/12/07/img1701936775.jpeg'),
('SP1701936773', 'third', './public/products/2023/12/07/img1701936776.jpeg'),
('SP1701937678', 'first', './public/products/2023/12/07/img1701937679.jpeg'),
('SP1701937678', 'fourth', './public/products/2023/12/07/img1701937682.jpeg'),
('SP1701937678', 'second', './public/products/2023/12/07/img1701937680.jpeg'),
('SP1701937678', 'third', './public/products/2023/12/07/img1701937681.jpeg'),
('SP1701946810', 'first', './public/products/2023/12/07/img1701946811.jpeg'),
('SP1701946810', 'fourth', './public/products/2023/12/07/img1701946814.jpeg'),
('SP1701946810', 'second', './public/products/2023/12/07/img1701946812.jpeg'),
('SP1701946810', 'third', './public/products/2023/12/07/img1701946813.jpeg'),
('SP1701947140', 'first', './public/products/2023/12/07/img1701947141.jpeg'),
('SP1701947140', 'fourth', './public/products/2023/12/07/img1701947144.jpeg'),
('SP1701947140', 'second', './public/products/2023/12/07/img1701947142.jpeg'),
('SP1701947140', 'third', './public/products/2023/12/07/img1701947143.jpeg'),
('SP1701947313', 'first', './public/products/2023/12/07/img1701947314.jpeg'),
('SP1701947313', 'fourth', './public/products/2023/12/07/img1701947317.jpeg'),
('SP1701947313', 'second', './public/products/2023/12/07/img1701947315.jpeg'),
('SP1701947313', 'third', './public/products/2023/12/07/img1701947316.jpeg'),
('SP1701947497', 'first', './public/products/2023/12/07/img1701947498.jpeg'),
('SP1701947497', 'fourth', './public/products/2023/12/07/img1701947501.jpeg'),
('SP1701947497', 'second', './public/products/2023/12/07/img1701947499.jpeg'),
('SP1701947497', 'third', './public/products/2023/12/07/img1701947500.jpeg'),
('SP1701947692', 'first', './public/products/2023/12/07/img1701947693.jpeg'),
('SP1701947692', 'fourth', './public/products/2023/12/07/img1701947696.jpeg'),
('SP1701947692', 'second', './public/products/2023/12/07/img1701947694.jpeg'),
('SP1701947692', 'third', './public/products/2023/12/07/img1701947695.jpeg');

INSERT INTO `Customers`(`email`, `password`, `full_name`, `phone`) VALUES ('n20dcat004@student.ptithcm.edu.vn','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3','Lê Văn Tèo','0707888555');
INSERT INTO `Customers`(`email`, `password`, `full_name`, `phone`) VALUES ('n20dcat017@student.ptithcm.edu.vn','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3','Lê Siêu Nhân','0707888555');

INSERT INTO `ShoppingCart`(`cart_code`, `email`) VALUES ('n20dcat004_9e44200d5e6dc13c6e4f1095a7233f77de4c9f80d8739f099e19f5f67e769aca','n20dcat004@student.ptithcm.edu.vn');
INSERT INTO `ShoppingCart`(`cart_code`, `email`) VALUES ('n20dcat017_e57c655f053c59890a80bf3106278e8e408f852a7f3fe953774a18bcea3119c0','n20dcat017@student.ptithcm.edu.vn');

INSERT INTO `CartItems`(`cart_code`, `product_code`, `quantity`, `size`, `total_price`) VALUES ('n20dcat004_9e44200d5e6dc13c6e4f1095a7233f77de4c9f80d8739f099e19f5f67e769aca','SP1701933882',3,'S',300);
INSERT INTO `CartItems`(`cart_code`, `product_code`, `quantity`, `size`, `total_price`) VALUES ('n20dcat004_9e44200d5e6dc13c6e4f1095a7233f77de4c9f80d8739f099e19f5f67e769aca','SP1701933882',3,'XL',300);
INSERT INTO `CartItems`(`cart_code`, `product_code`, `quantity`, `size`, `total_price`) VALUES ('n20dcat004_9e44200d5e6dc13c6e4f1095a7233f77de4c9f80d8739f099e19f5f67e769aca','SP1701935173',1,'L',200);

INSERT INTO `CartItems`(`cart_code`, `product_code`, `quantity`, `size`, `total_price`) VALUES ('n20dcat017_e57c655f053c59890a80bf3106278e8e408f852a7f3fe953774a18bcea3119c0','SP1701936580',1,'L',400);

INSERT INTO `Orders`(`order_code`, `state`, `total_price`, `email`, `address`) VALUES ('order_2','pending',900,'n20dcat004@student.ptithcm.edu.vn','Phường Phú Thạnh, Quận Gò Vấp, Thành phố Hồ Chí Minh');
INSERT INTO `Orders`(`order_code`, `state`, `total_price`, `email`, `address`) VALUES ('order_4','delivering',1000,'n20dcat017@student.ptithcm.edu.vn','Phường Phú Thạnh, Quận Gò Vấp, Thành phố Hồ Chí Minh');

INSERT INTO `OrderItems`(`order_code`, `product_code`, `quantity`, `size`, `total_price`) VALUES ('order_2','SP1701934808',3,'L',300);
INSERT INTO `OrderItems`(`order_code`, `product_code`, `quantity`, `size`, `total_price`) VALUES ('order_2','SP1701935173',3,'M',600);
INSERT INTO `OrderItems`(`order_code`, `product_code`, `quantity`, `size`, `total_price`) VALUES ('order_4','SP1701935684',5,'L',1000);

INSERT INTO `OrdersHistory`(`order_code`, `order_date` ,`payment_code`, `payment_date`, `payment_type`, `state`, `email`, `address`, `phone`, `total_price`) VALUES ('order_1', CURRENT_TIMESTAMP,'payment_1',CURRENT_TIMESTAMP,'bank_transfer','delivered','n20dcat004@student.ptithcm.edu.vn','Phường Phú Thạnh, Quận Gò Vấp, Thành phố Hồ Chí Minh','0707888555',400);
INSERT INTO `OrdersHistory`(`order_code`, `order_date` , `state`, `email`, `address`, `phone`, `total_price`) VALUES ('order_3', CURRENT_TIMESTAMP,'cancelled','n20dcat017@student.ptithcm.edu.vn','Phường Phú Thạnh, Quận Gò Vấp, Thành phố Hồ Chí Minh','0707888555',800);

INSERT INTO `OrdersHistoryItems`(`order_code`, `product_code`, `quantity`, `size`, `total_price`) VALUES ('order_1','SP1701933882',2,'S', 200);
INSERT INTO `OrdersHistoryItems`(`order_code`, `product_code`, `quantity`, `size`, `total_price`) VALUES ('order_1','SP1701934515',2,'L', 200);
INSERT INTO `OrdersHistoryItems`(`order_code`, `product_code`, `quantity`, `size`, `total_price`) VALUES ('order_3','SP1701935516',4,'L', 800);