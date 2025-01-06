let plannerTitle = document.querySelectorAll('.plannerTitle');
let hashtag = document.querySelectorAll('.hashtag');

document.addEventListener('DOMContentLoaded', function() {
    for (let i = 0; i < plannerTitle.length; i++) {
        let address = plannerTitle[i].textContent || plannerTitle[i].innerText;
        let result = truncateText(address, 10);
        plannerTitle[i].innerText = result; // 가공된 데이터를 다시 innerText로 설정
    }

    for (let i = 0; i < hashtag.length; i++) {
        let address = hashtag[i].textContent || hashtag[i].innerText;
        let result = extractAddr(address);
        hashtag[i].innerText = result; // 가공된 데이터를 다시 innerText로 설정
    }
});

function extractAddr(address) {
    const districtPattern = /[가-힣]+구/g;
    const cityOrCountyPattern = /[가-힣]+(?:시|군)/g;

    const districtMatch = address.match(districtPattern);
    if (districtMatch) {
        return districtMatch[0];
    }

    const cityOrCountyMatch = address.match(cityOrCountyPattern);
    return cityOrCountyMatch ? cityOrCountyMatch[0] : null;
}

function truncateText(text, maxLength) {
    if (text.length > maxLength) {
        return text.slice(0, maxLength) + '...';
    }
    return text;
}
