# Vibe Mood Meal — AI Vibe Coding 小作品

這是一個符合期末黑客松需求的小型全端系統：

- Java 後端：Spring Boot REST API
- Database：SQLite 儲存使用者心情紀錄與推薦結果
- Frontend：HTML / CSS / JavaScript 串接 API
- DM / ML：用簡單規則式分類模型，依照心情、壓力、時間推薦餐點與提醒

## Demo 功能

使用者輸入：

1. 今天的心情
2. 壓力分數 1–10
3. 可用時間
4. 備註

系統會：

1. 呼叫 Java API
2. 後端使用 ML-like 規則模型分析狀態
3. 產生餐點推薦、關懷提醒、風險等級
4. 寫入 SQLite Database
5. 前端重新讀取歷史紀錄並顯示

## 專案架構

```text
vibe-mood-meal/
├── pom.xml
├── render.yaml
├── README.md
└── src/
    └── main/
        ├── java/com/example/moodmeal/
        │   ├── MoodMealApplication.java
        │   ├── MoodMealController.java
        │   ├── MoodMealService.java
        │   ├── MoodMealRepository.java
        │   ├── RecommendationModel.java
        │   ├── MoodRequest.java
        │   └── MoodRecord.java
        └── resources/static/
            ├── index.html
            ├── style.css
            └── app.js
```

## 資料流向

```text
使用者輸入表單
      ↓
Frontend app.js 呼叫 POST /api/recommend
      ↓
Spring Boot Controller 接收 JSON
      ↓
RecommendationModel 進行規則式 DM/ML 分析
      ↓
Repository 將結果寫入 SQLite
      ↓
Frontend 呼叫 GET /api/records
      ↓
畫面顯示推薦結果與歷史紀錄
```

## 前後端如何串接

前端 `app.js` 使用 `fetch()` 呼叫後端 API：

- `POST /api/recommend`：送出使用者心情資料，取得推薦結果
- `GET /api/records`：取得所有歷史紀錄
- `DELETE /api/records`：清空紀錄，方便 Demo 重新開始

後端 `MoodMealController` 負責接收前端請求，並把資料交給 `MoodMealService` 處理。

## 資料庫如何協作

`MoodMealRepository` 啟動時會自動建立 SQLite 資料表 `mood_records`。
每次使用者送出表單後，後端會把以下資料存入 DB：

- mood
- stress level
- available time
- note
- recommended meal
- advice
- risk level
- created_at

Render 部署時，SQLite 檔案會在伺服器執行環境中自動產生。

## ML 模組如何嵌入

本作品使用 `RecommendationModel` 作為簡化版 DM/ML 模組。
它根據使用者輸入做規則式分類：

- 壓力 8–10：High risk，推薦舒壓型飲食與休息提醒
- 壓力 5–7：Medium risk，推薦均衡餐點
- 壓力 1–4：Low risk，推薦輕食或正常餐點
- 若備註含有 tired / sleepy / exam / deadline 等關鍵字，會調整提醒內容

雖然不是大型機器學習模型，但已呈現「輸入資料 → 模型分析 → 商務邏輯 → 推薦結果」的完整資料流程。

## Local 端執行方式

請先確認有安裝：

- JDK 17+
- Maven

在 VS Code Terminal 輸入：

```bash
mvn clean package
mvn spring-boot:run
```

成功後打開：

```text
http://localhost:8080
```

## Render 部署方式

1. 把整個專案 push 到 GitHub
2. 到 Render 建立 New Web Service
3. 連接你的 GitHub repository
4. 設定：

```text
Environment: Java
Build Command: mvn clean package -DskipTests
Start Command: java -jar target/vibe-mood-meal-0.0.1-SNAPSHOT.jar
```

5. Deploy 後取得 Live Demo URL

## 繳交內容建議

- Live Demo URL：Render 網址
- GitHub Repository URL：你的 GitHub repo
- README.md：本檔案
- Prompt 紀錄：匯出 ChatGPT / Cursor 對話紀錄
