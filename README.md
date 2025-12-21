# 🏋️‍♂️ WorkoutApp – 운동하는 단국인

Android 기반 개인 운동 기록 및 관리 앱  
운동 선택 → 계획 → 수행 → 결과 저장 → 기록 조회까지 한 번에 관리할 수 있는 **운동 루틴 앱**입니다.

---

## 📱 주요 기능

### 1️⃣ 운동 목록 & 검색
- 부위별 운동 필터링
- 운동명 검색 기능
- 운동 클릭 시 상세 화면 이동

### 2️⃣ 운동 상세 화면
- 운동 이미지 제공
- 타겟 근육 표시
- 운동 설명
- 운동 담기 기능

### 3️⃣ 운동 계획
- 담은 운동 리스트 확인
- 드래그 & 드롭으로 순서 변경
- 운동 삭제
- 운동 시작

### 4️⃣ 운동 수행
- 전체 운동 타이머
- 세트별 무게 / 횟수 입력
- 휴식 타이머
- 일시정지 / 재생
- **Gemini AI 질문 기능**

### 5️⃣ 운동 결과 & 기록
- 총 세트 수
- 총 반복 수
- 총 중량
- 총 운동 시간
- 날짜별 운동 기록 조회

---

## 🧠 앱 구조 (Activity Flow)

```text
MainActivity  
↓  
ExerciseListActivity  
↓  
ExerciseDetailActivity  
↓  
ExercisePlanActivity  
↓  
ExercisePerformActivity  
├─ Gemini AI (Q&A)  
└─ Room Database (WorkoutSession / WorkoutSet 저장)  
↓  
WorkoutResultActivity  
└─ Room Database (운동 결과 조회 및 요약 계산)  
↓  
WorkoutHistoryActivity
```

---

## 🗄️ 데이터베이스 구조 (Room)

### Entities
- **WorkoutSession**
  - 날짜
  - 시작 시간 / 종료 시간
  - 총 운동 시간

- **WorkoutSet**
  - 운동 이름
  - 세트 번호
  - 무게
  - 반복 횟수

---

## 🤖 AI 기능 (Gemini API)
- 운동 수행 중 Gemini AI에게 질문 가능
- 운동 자세, 방법 안내
- BottomSheet UI 제공

---

## 🛠️ 기술 스택
- Java
- Android XML
- RecyclerView
- Room Database
- Gemini API

---

## 🔐 보안 처리
- Gemini API Key는 `local.properties`에 저장
- `.gitignore`로 GitHub 업로드 제외

---

## 📌 실행 방법
1. Android Studio에서 프로젝트 열기
2. `local.properties`에 Gemini API Key 추가
3. Emulator 또는 실제 기기에서 실행

