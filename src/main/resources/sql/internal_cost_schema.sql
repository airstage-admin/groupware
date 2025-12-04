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

-- 07_users（ユーザー）
-- department: 1=管理者, 2=決裁者, 3=総務部, 4=インフラ部, 5=開発部
INSERT INTO users (id, login_id, password, user_name, employee_no, mail, employee_type, department, hire_date, paid_grant_date, paid_leave_granted, paid_leave_remaining, delflg, created_by, created_at, updated_by, updated_at) VALUES
(1, 'yamada', 'password123', '山田 太郎', '001', 'yamada@example.com', 1, 1, '2020-04-01', '2021-04-01', 20, 15.5, 0, 1, NOW(), 1, NOW()),
(2, 'suzuki', 'password123', '鈴木 花子', '002', 'suzuki@example.com', 1, 2, '2019-04-01', '2020-04-01', 20, 12.0, 0, 1, NOW(), 1, NOW()),
(3, 'tanaka', 'password123', '田中 一郎', '003', 'tanaka@example.com', 1, 3, '2021-10-01', '2022-10-01', 15, 10.0, 0, 1, NOW(), 1, NOW()),
(4, 'sato', 'password123', '佐藤 美咲', '004', 'sato@example.com', 1, 4, '2018-04-01', '2019-04-01', 20, 8.5, 0, 1, NOW(), 1, NOW()),
(5, 'takahashi', 'password123', '高橋 健太', '005', 'takahashi@example.com', 1, 5, '2022-04-01', '2023-04-01', 12, 12.0, 0, 1, NOW(), 1, NOW()),
(6, 'ito', 'password123', '伊藤 直樹', '006', 'ito@example.com', 1, 2, '2020-07-01', '2021-07-01', 18, 14.0, 0, 1, NOW(), 1, NOW()),
(7, 'watanabe', 'password123', '渡辺 さくら', '007', 'watanabe@example.com', 1, 1, '2023-04-01', '2024-04-01', 10, 10.0, 0, 1, NOW(), 1, NOW()),
(8, 'kobayashi', 'password123', '小林 大輔', '008', 'kobayashi@example.com', 1, 3, '2017-04-01', '2018-04-01', 20, 5.0, 0, 1, NOW(), 1, NOW()),
(9, 'nakamura', 'password123', '中村 愛', '009', 'nakamura@example.com', 1, 5, '2021-04-01', '2022-04-01', 15, 11.5, 0, 1, NOW(), 1, NOW()),
(10, 'kato', 'password123', '加藤 翔太', '010', 'kato@example.com', 1, 4, '2019-10-01', '2020-10-01', 18, 9.0, 0, 1, NOW(), 1, NOW());

-- 08_extend_role（拡張権限）
-- role: 1=一般, 2=管理者, 3=決裁者
INSERT INTO extend_role (target_user_id, role) VALUES
(1, 2),
(11, 2),
(17, 2),
(12, 3),
(16, 3);