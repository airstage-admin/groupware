package com.groupware.common.config;

import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseConfigurator {
    public static void configureDatabaseProperties() {
        try {
            // .envを読み込む
            Dotenv dotenv = Dotenv.load();

            // 環境変数として設定
            // ※ Spring Bootがこれらのシステムプロパティを自動的にデータソース設定に利用します
            System.setProperty("spring.datasource.url", dotenv.get("DB_URL"));
            System.setProperty("spring.datasource.username", dotenv.get("DB_USERNAME"));
            System.setProperty("spring.datasource.password", dotenv.get("DB_PASSWORD"));
            
            System.out.println("DB接続設定をシステムプロパティに適用しました。");

        } catch (Exception e) {
            // エラーハンドリング（.envファイルが見つからない、キーがないなど）
            System.err.println("DB接続設定の読み込み中にエラーが発生しました: " + e.getMessage());
            // 処理を継続させたくない場合は例外を再スローする
            throw new RuntimeException("DB設定初期化失敗", e);
        }
    }
}
