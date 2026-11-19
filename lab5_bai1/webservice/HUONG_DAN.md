# Lab 5 - Bai 1: Huong dan chay webservice XAMPP + test bang Postman

## 1. Cai XAMPP, copy webservice
1. Cai XAMPP, bat 2 module `Apache` + `MySQL` trong XAMPP Control Panel.
2. Copy 2 file `db_config.php`, `login.php` vao:
   `C:\xampp\htdocs\lab5\`
3. Mo phpMyAdmin: http://localhost/phpmyadmin
   - Tab `Import` -> chon file `webservice/db.sql` -> `Go`.
   - Kiem tra DB `lab5`, bang `Users` co 2 dong: minhtien/1234, admin/admin.

## 2. Test webservice bang Postman (debug truoc khi chay app)
- Method: `POST`
- URL Emulator: `http://localhost/lab5/login.php` (test ngay tren PC)
- Body -> `x-www-form-urlencoded`:
  - `username` = `minhtien`
  - `password` = `1234`
- Ket qua dung:
```json
{ "success": true, "message": "Dang nhap thanh cong!", "id": 1, "username": "minhtien" }
```
- Ket qua sai pass:
```json
{ "success": false, "message": "Sai ten dang nhap hoac mat khau!" }
```

## 3. URL dung trong app Android (RetrofitClient.java)
- Chay tren **Emulator**: giu nguyen `http://10.0.2.2/lab5/`
- Chay tren **dien thoai that** (chung WiFi voi PC):
  doi thanh `http://<IP-cua-PC>/lab5/`  VD: `http://192.168.1.10/lab5/`
- Khong dung `http://localhost/` trong app Android.

## 4. Chay app
1. Android Studio -> Sync Gradle (da them `retrofit:2.9.0`, `converter-gson:2.9.0`).
2. Run app -> man hinh "Dang nhap he thong" (giao dien Lab 4 giu nguyen:
   header xanh #3F51B5 "Shared Preferences KPT", tieu de do, gach chan xam/hong,
   checkbox hong "Nho dang nhap", nut xam "XAC NHAN").
3. Nhap `minhtien` / `1234` -> Toast "Dang nhap thanh cong!" -> sang man hinh chao.
4. Tick "Nho dang nhap" thi lan sau mo app vao thang (SharedPreferences giong Lab 4).
   Nut "DANG XUAT" de xoa trang thai va ve login.
