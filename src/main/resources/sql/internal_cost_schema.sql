-- ============================================
-- 経費精算システム テーブル定義
-- ============================================

-- 勘定科目マスタ
CREATE TABLE IF NOT EXISTS subjects (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(256) NOT NULL,
    description VARCHAR(256)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ステータスマスタ
CREATE TABLE IF NOT EXISTS status (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(256) NOT NULL DEFAULT '0',
    preset_color INT DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 会社情報
CREATE TABLE IF NOT EXISTS company_information (
    company_id BIGINT PRIMARY KEY,
    fy_begin_month INT DEFAULT 4,
    fy_end_month INT DEFAULT 3
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- プロジェクト
CREATE TABLE IF NOT EXISTS projects (
    company_id BIGINT NOT NULL,
    projects_id INT NOT NULL,
    projects_name VARCHAR(128),
    projects_concept VARCHAR(1024),
    PRIMARY KEY (company_id, projects_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 申請
CREATE TABLE IF NOT EXISTS applications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    recognized_id VARCHAR(20),
    applicant_user_id INT,
    applicant_department_name VARCHAR(256),
    role TINYINT DEFAULT 0,
    target_user_id INT,
    applicant_name VARCHAR(256),
    apply_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    purchased_date DATE,
    content VARCHAR(256),
    payment_method INT DEFAULT 0,
    subject INT,
    price INT,
    status INT,
    image_url VARCHAR(512),
    refused_reason VARCHAR(1024),
    projects_id INT,
    need_refund INT DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 申請履歴
CREATE TABLE IF NOT EXISTS apply_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    application_id INT,
    applier_user_id INT,
    submitted_user_id INT,
    status INT,
    refused_reason VARCHAR(1024),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 拡張権限
-- role: 1...一般, 2...管理者, 3...決裁者
CREATE TABLE IF NOT EXISTS extend_role (
    target_user_id INT NOT NULL,
    role INT NOT NULL,
    PRIMARY KEY (target_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- ============================================
-- テストデータ INSERT
-- ============================================

-- 01_subjects（勘定科目）
INSERT INTO subjects (id, name, description) VALUES
(1, '旅費交通費', '従業員が業務のために通常の勤務地を離れて移動する際に発生する費用'),
(2, '交際費', '取引先や顧客との接待、贈答品などにかかる費用'),
(3, '消耗品費', '事務用品、文房具、備品など、短期間で消費される物品の購入費用'),
(4, '通信費', '業務で使用する電話、インターネット、郵便などの通信にかかる費用'),
(5, '出張費', '出張にかかる費用');

-- 02_applications（申請）
-- テストデータなし

-- 03_status（ステータス）
INSERT INTO status (id, status, preset_color) VALUES
(1, '承認待ち', 2),
(2, '承認済み', 3),
(3, '差し戻し', 1);

-- 04_apply_history（申請履歴）
-- テストデータなし

-- 05_company_information（会社情報）
INSERT INTO company_information (company_id, fy_begin_month, fy_end_month) VALUES
(99999, 4, 3);

-- 06_projects（プロジェクト）
INSERT INTO projects (company_id, projects_id, projects_name, projects_concept) VALUES
(99999, 1000, 'OO社サーバーリプレイス案件', 'OO社様の老朽化したサーバーシステムをモダンな構成にフルリプレイスするPJ');
