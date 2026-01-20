CREATE DATABASE IF NOT EXISTS taskban;
USE taskban;

-- 1. Tabela USERS
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- 2. Tabela BOARDS
CREATE TABLE boards (
    board_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    
    -- Coluna FK com nome explícito para facilitar leitura
    fk_id_user INT NOT NULL,
    
    CONSTRAINT fk_boards_user 
        FOREIGN KEY (fk_id_user) REFERENCES users(user_id) 
        ON DELETE CASCADE
);

-- 3. Tabela CARDS
CREATE TABLE cards (
    card_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    deadline DATETIME,
    status ENUM('todo', 'in_progress', 'done') NOT NULL DEFAULT 'todo',
    position INT NOT NULL DEFAULT 0,
    
    -- Coluna FK com nome explícito para facilitar leitura
    fk_id_board INT NOT NULL, 
    
    CONSTRAINT fk_cards_board 
        FOREIGN KEY (fk_id_board) REFERENCES boards(board_id) 
        ON DELETE CASCADE
);

