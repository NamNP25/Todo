CREATE DATABASE IF NOT EXISTS todolist_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE todolist_db;

-- 1. Bảng Users
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('USER', 'ADMIN') DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Bảng Todos
CREATE TABLE IF NOT EXISTS todos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status ENUM('TODO', 'IN_PROGRESS', 'DONE') NOT NULL DEFAULT 'TODO',
    due_date DATE,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_todo_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_todo_user (user_id),
    INDEX idx_todo_status (status)
);

-- 3. Bảng Categories
CREATE TABLE IF NOT EXISTS categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. Bảng trung gian (Many-to-Many)
CREATE TABLE IF NOT EXISTS todo_categories (
    todo_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (todo_id, category_id),
    CONSTRAINT fk_tc_todo FOREIGN KEY (todo_id) REFERENCES todos(id) ON DELETE CASCADE,
    CONSTRAINT fk_tc_category FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);

-- DỮ LIỆU MẪU (Đã xóa cột 'completed' bị thừa)
INSERT INTO users (username, email, password, role)
VALUES 
('nam', 'nam@gmail.com', '123456', 'USER'),
('admin', 'admin@gmail.com', 'admin123', 'ADMIN');

INSERT INTO categories (name)
VALUES ('Study'), ('Work'), ('Personal');

INSERT INTO todos (title, description, status, due_date, user_id)
VALUES 
('Học MySQL', 'Ôn lại SELECT, JOIN', 'TODO', '2025-01-10', 1),
('Làm CRUD Todo', 'Spring Boot + JPA', 'IN_PROGRESS', '2025-01-15', 1),
('Deploy project', 'Deploy lên VPS', 'DONE', '2025-01-05', 1);

INSERT INTO todo_categories (todo_id, category_id)
VALUES (1, 1), (2, 1), (2, 2), (3, 2);


INSERT INTO users (id, username, email, password, role) 
VALUES (1, 'nam', 'nam@gmail.com', '123456', 'USER');
-- Chèn Todos với ID cố định là 1, 2, 3
INSERT INTO todos (id, title, description, status, due_date, user_id)
VALUES 
(1, 'Học MySQL', 'Ôn lại SELECT, JOIN', 'TODO', '2025-01-10', 1),
(2, 'Làm CRUD Todo', 'Spring Boot + JPA', 'IN_PROGRESS', '2025-01-15', 1),
(3, 'Deploy project', 'Deploy lên VPS', 'DONE', '2025-01-05', 1);

-- Bây giờ lệnh này sẽ CHẮC CHẮN chạy thành công
INSERT INTO todo_categories (todo_id, category_id)
VALUES (1, 1), (2, 1), (2, 2), (3, 2);

INSERT INTO todos (id, title, description, status, due_date, user_id) 
VALUES 
(1, 'Học MySQL', 'Ôn lại SELECT, JOIN', 'TODO', '2025-01-10', 1),
(2, 'Làm CRUD Todo', 'Spring Boot + JPA', 'IN_PROGRESS', '2025-01-15', 1),
(3, 'Deploy project', 'Deploy lên VPS', 'DONE', '2025-01-05', 1);