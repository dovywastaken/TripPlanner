<!DOCTYPE html>
<html>
<head>
    <style>
        .filter-container {
            font-family: 'Arial', sans-serif;
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
        }

        .filter-section {
            display: flex;
            gap: 20px;
            margin-bottom: 20px;
        }

        .filter-group {
            flex: 1;
            border: 1px solid #ddd;
            padding: 10px;
        }

        .filter-title {
            font-weight: bold;
            margin-bottom: 10px;
            padding-bottom: 5px;
            border-bottom: 1px solid #eee;
        }

        .filter-options {
            display: flex;
            flex-direction: column;
            gap: 8px;
            max-height: 400px;
            overflow-y: auto;
        }

        .filter-option {
            padding: 8px 12px;
            background-color: #f5f5f5;
            border: 1px solid #ddd;
            border-radius: 4px;
            cursor: pointer;
            transition: all 0.2s;
        }

        .filter-option:hover {
            background-color: #e9e9e9;
        }

        .filter-option.selected {
            background-color: #007bff;
            color: white;
            border-color: #0056b3;
        }

        .sub-region {
            margin-top: 10px;
            padding-top: 10px;
            border-top: 1px solid #eee;
            display: none;
        }

        .sub-region.visible {
            display: block;
        }

        .action-buttons {
            display: flex;
            gap: 10px;
            justify-content: flex-end;
        }

        .action-button {
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: all 0.2s;
        }

        .select-button {
            background-color: #f8f9fa;
            border: 1px solid #ddd;
        }

        .reset-button {
            background-color: #6c757d;
            color: white;
        }

        .action-button:hover {
            opacity: 0.9;
        }

        #selected-filters {
            margin-top: 20px;
            padding: 10px;
            background-color: #f8f9fa;
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <div class="filter-container">
        <div class="filter-section">
            <div class="filter-group">
                <div class="filter-title">관광 타입</div>
                <div class="filter-options" id="tourType">
                    <div class="filter-option" data-value="tourist">관광지</div>
                    <div class="filter-option" data-value="culture">문화시설</div>
                    <div class="filter-option" data-value="festival">축제공연</div>
                    <div class="filter-option" data-value="leisure">레포츠</div>
                    <div class="filter-option" data-value="shopping">쇼핑</div>
                </div>
            </div>
            
            <div class="filter-group">
                <div class="filter-title">지역</div>
                <div class="filter-options" id="region">
                    <div class="filter-option" data-value="seoul">서울특별시</div>
                    <div class="filter-option" data-value="busan">부산광역시</div>
                    <div class="filter-option" data-value="daegu">대구광역시</div>
                    <div class="filter-option" data-value="incheon">인천광역시</div>
                    <div class="filter-option" data-value="gwangju">광주광역시</div>
                    <div class="filter-option" data-value="daejeon">대전광역시</div>
                    <div class="filter-option" data-value="ulsan">울산광역시</div>
                    <div class="filter-option" data-value="sejong">세종특별자치시</div>
                    <div class="filter-option" data-value="gyeonggi">경기도</div>
                    <div class="filter-option" data-value="gangwon">강원도</div>
                    <div class="filter-option" data-value="chungbuk">충청북도</div>
                    <div class="filter-option" data-value="chungnam">충청남도</div>
                    <div class="filter-option" data-value="jeonbuk">전라북도</div>
                    <div class="filter-option" data-value="jeonnam">전라남도</div>
                    <div class="filter-option" data-value="gyeongbuk">경상북도</div>
                    <div class="filter-option" data-value="gyeongnam">경상남도</div>
                    <div class="filter-option" data-value="jeju">제주특별자치도</div>
                </div>
                <div class="sub-region" id="subRegion">
                    <!-- 하위 지역은 JavaScript로 동적 생성됨 -->
                </div>
            </div>
        </div>
        
        <div class="action-buttons">
            <button class="action-button select-button">선택</button>
            <button class="action-button reset-button">초기화</button>
        </div>

        <div id="selected-filters">
            선택된 필터: <span id="filter-display"></span>
        </div>
    </div>

    <script>
        // 지역 데이터 (가나다순 정렬)
        const regionData = {
            seoul: [
                "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", 
                "금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구",
                "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구",
                "은평구", "종로구", "중구", "중랑구"
            ].sort(),
            busan: [
                "강서구", "금정구", "남구", "동구", "동래구", "부산진구", "북구",
                "사상구", "사하구", "서구", "수영구", "연제구", "영도구", "중구",
                "해운대구"
            ].sort(),
            daegu: [
                "남구", "달서구", "달성군", "동구", "북구", "서구", "수성구", "중구"
            ].sort(),
            incheon: [
                "강화군", "계양구", "남동구", "동구", "미추홀구", "부평구", "서구", 
                "연수구", "옹진군", "중구"
            ].sort(),
            gwangju: [
                "광산구", "남구", "동구", "북구", "서구"
            ].sort(),
            daejeon: [
                "대덕구", "동구", "서구", "유성구", "중구"
            ].sort(),
            ulsan: [
                "남구", "동구", "북구", "울주군", "중구"
            ].sort(),
            sejong: ["세종특별자치시"],
            gyeonggi: [
                "가평군", "고양시", "과천시", "광명시", "광주시", "구리시", "군포시",
                "김포시", "남양주시", "동두천시", "부천시", "성남시", "수원시", "시흥시",
                "안산시", "안성시", "안양시", "양주시", "양평군", "여주시", "연천군",
                "오산시", "용인시", "의왕시", "의정부시", "이천시", "파주시", "평택시",
                "포천시", "하남시", "화성시"
            ].sort(),
            gangwon: [
                "강릉시", "고성군", "동해시", "삼척시", "속초시", "양구군", "양양군",
                "영월군", "원주시", "인제군", "정선군", "철원군", "춘천시", "태백시",
                "평창군", "홍천군", "화천군", "횡성군"
            ].sort(),
            chungbuk: [
                "괴산군", "단양군", "보은군", "영동군", "옥천군", "음성군", "제천시",
                "증평군", "진천군", "청주시", "충주시"
            ].sort(),
            chungnam: [
                "계룡시", "공주시", "금산군", "논산시", "당진시", "보령시", "부여군",
                "서산시", "서천군", "아산시", "예산군", "천안시", "청양군", "태안군",
                "홍성군"
            ].sort(),
            jeonbuk: [
                "고창군", "군산시", "김제시", "남원시", "무주군", "부안군", "순창군",
                "완주군", "익산시", "임실군", "장수군", "전주시", "정읍시", "진안군"
            ].sort(),
            jeonnam: [
                "강진군", "고흥군", "곡성군", "광양시", "구례군", "나주시", "담양군",
                "목포시", "무안군", "보성군", "순천시", "신안군", "여수시", "영광군",
                "영암군", "완도군", "장성군", "장흥군", "진도군", "함평군", "해남군",
                "화순군"
            ].sort(),
            gyeongbuk: [
                "경산시", "경주시", "고령군", "구미시", "군위군", "김천시", "문경시",
                "봉화군", "상주시", "성주군", "안동시", "영덕군", "영양군", "영주시",
                "영천시", "예천군", "울릉군", "울진군", "의성군", "청도군", "청송군",
                "칠곡군", "포항시"
            ].sort(),
            gyeongnam: [
                "거제시", "거창군", "고성군", "김해시", "남해군", "밀양시", "사천시",
                "산청군", "양산시", "의령군", "진주시", "창녕군", "창원시", "통영시",
                "하동군", "함안군", "함양군", "합천군"
            ].sort(),
            jeju: [
                "서귀포시", "제주시"
            ].sort()
        };

        let selectedFilters = {
            tourType: null,
            region: null,
            subRegion: null
        };

        document.addEventListener('DOMContentLoaded', function() {
            const filterOptions = document.querySelectorAll('.filter-option');
            const subRegionDiv = document.getElementById('subRegion');

            filterOptions.forEach(option => {
                option.addEventListener('click', function() {
                    const parentGroup = this.parentElement;
                    const filterType = parentGroup.id;
                    
                    parentGroup.querySelectorAll('.filter-option').forEach(opt => {
                        opt.classList.remove('selected');
                    });
                    
                    this.classList.add('selected');
                    selectedFilters[filterType] = this.dataset.value;

                    if (filterType === 'region') {
                        showSubRegions(this.dataset.value);
                        selectedFilters.subRegion = null;
                    }
                    
                    updateFilterDisplay();
                });
            });

            const resetButton = document.querySelector('.reset-button');
            resetButton.addEventListener('click', function() {
                filterOptions.forEach(option => {
                    option.classList.remove('selected');
                });
                subRegionDiv.innerHTML = '';
                subRegionDiv.classList.remove('visible');
                selectedFilters = {
                    tourType: null,
                    region: null,
                    subRegion: null
                };
                updateFilterDisplay();
            });

            const selectButton = document.querySelector('.select-button');
            selectButton.addEventListener('click', function() {
                console.log('현재 선택된 필터:', selectedFilters);
            });
        });

        function showSubRegions(region) {
            const subRegionDiv = document.getElementById('subRegion');
            const subRegions = regionData[region] || [];
            
            if (subRegions.length > 0) {
                let html = '<div class="filter-title">세부 지역</div><div class="filter-options">';
                subRegions.forEach(subRegion => {
                    html += `<div class="filter-option" data-value="${subRegion}">${subRegion}</div>`;
                });
                html += '</div>';
                
                subRegionDiv.innerHTML = html;
                subRegionDiv.classList.add('visible');

                const subRegionOptions = subRegionDiv.querySelectorAll('.filter-option');
                subRegionOptions.forEach(option => {
                    option.addEventListener('click', function() {
                        subRegionOptions.forEach(opt => opt.classList.remove('selected'));
                        this.classList.add('selected');
                        selectedFilters.subRegion = this.dataset.value;
                        updateFilterDisplay();
                    });
                });
            } else {
                subRegionDiv.innerHTML = '';
                subRegionDiv.classList.remove('visible');
            }
        }

        function updateFilterDisplay() {
            const displayElement = document.getElementById('filter-display');
            const selectedItems = [];
            
            if (selectedFilters.tourType) {
                const tourOption = document.querySelector(`#tourType [data-value="${selectedFilters.tourType}"]`);
                if (tourOption) selectedItems.push(tourOption.textContent);
            }
            
            if (selectedFilters.region) {
                const regionOption = document.querySelector(`#region [data-value="${selectedFilters.region}"]`);
                if (regionOption) {
                    let regionText = regionOption.textContent;
                    if (selectedFilters.subRegion) {
                        regionText += ` > ${selectedFilters.subRegion}`;
                    }
                    selectedItems.push(regionText);
                }
            }
            
            displayElement.textContent = selectedItems.join(', ') || '없음';
        }
    </script>
</body>
</html>