<?php
// Lab 5 - Bai 1: Webservice dang nhap (bang Users: id, username, password MD5)
// URL test: POST http://10.0.2.2/lab5/login.php  (Emulator)
//           POST http://<IP-PC>/lab5/login.php   (may that, chung WiFi)
// Body (x-www-form-urlencoded): username=minhtien&password=1234
header('Content-Type: application/json; charset=utf-8');
require_once __DIR__ . '/db_config.php';

$username = isset($_POST['username']) ? trim($_POST['username']) : '';
$password = isset($_POST['password']) ? trim($_POST['password']) : '';

if ($username === '' || $password === '') {
    echo json_encode(array(
        "success" => false,
        "message" => "Vui long nhap day du ten dang nhap va mat khau!"
    ), JSON_UNESCAPED_UNICODE);
    exit;
}

// Chu y: password luu MD5 trong DB -> ma hoa input roi so sanh (theo yeu cau de bai)
$md5pass = md5($password);

$stmt = $conn->prepare("SELECT id, username FROM Users WHERE username = ? AND password = ? LIMIT 1");
$stmt->bind_param("ss", $username, $md5pass);
$stmt->execute();
$result = $stmt->get_result();

if ($row = $result->fetch_assoc()) {
    echo json_encode(array(
        "success"  => true,
        "message"  => "Dang nhap thanh cong!",
        "id"       => (int)$row["id"],
        "username" => $row["username"]
    ), JSON_UNESCAPED_UNICODE);
} else {
    echo json_encode(array(
        "success" => false,
        "message" => "Sai ten dang nhap hoac mat khau!"
    ), JSON_UNESCAPED_UNICODE);
}

$stmt->close();
$conn->close();
