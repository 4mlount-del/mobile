<?php
// Lab 5 - Bai 1: config ket noi MySQL (XAMPP mac dinh: root / khong mat khau)
$DB_HOST = "localhost";
$DB_USER = "root";
$DB_PASS = "";
$DB_NAME = "lab5";

$conn = new mysqli($DB_HOST, $DB_USER, $DB_PASS, $DB_NAME);
if ($conn->connect_error) {
    header('Content-Type: application/json; charset=utf-8');
    die(json_encode(array(
        "success" => false,
        "message" => "Ket noi CSDL that bai: " . $conn->connect_error
    ), JSON_UNESCAPED_UNICODE));
}
$conn->set_charset("utf8mb4");
