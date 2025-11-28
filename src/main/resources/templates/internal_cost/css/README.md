# Internal Cost CSS フォルダ

## 概要
このフォルダは経費精算（internal_cost）機能のCSSに関する情報を管理しています。

## CSSファイルの場所
実際のCSSファイルは以下の場所に配置されています：

```
src/main/resources/static/css/internal_cost/internal_cost.css
```

## CSSの読み込み方法
HTMLテンプレートでは、Thymeleafの構文を使って以下のようにCSSを読み込みます：

```html
<link rel="stylesheet" th:href="@{/css/internal_cost/internal_cost.css}">
```

## ファイル構成

### internal_cost.css
メインのスタイルシートです。以下の要素が含まれます：

1. **カラーパレット（CSS Variables）**
   - ナビゲーションバー: 青色～ライトブルーのグラデーション
   - 背景色: グレーがかった白 (#F5F6FA)
   - コンテンツカード: 白 (#FFFFFF)

2. **レイアウト**
   - `.app-container` - メインコンテナ
   - `.nav-sidebar` - 左サイドナビゲーション
   - `.main-content` - メインコンテンツエリア

3. **コンポーネント**
   - `.nav-item` - ナビゲーション項目
   - `.content-card` - コンテンツカード
   - `.stat-card` - 統計カード
   - `.btn` - ボタン
   - `.data-table` - データテーブル
   - `.form-*` - フォーム要素

4. **アニメーション**
   - `fadeIn` - フェードインアニメーション

## カスタマイズ
CSSカスタムプロパティ（CSS Variables）を使用しているため、`:root`内の変数を変更することで簡単にカラーテーマを変更できます。

```css
:root {
    --nav-gradient-start: #0052D4;
    --nav-gradient-middle: #4364F7;
    --nav-gradient-end: #6FB1FC;
    --bg-main: #F5F6FA;
    --bg-content: #FFFFFF;
}
```

