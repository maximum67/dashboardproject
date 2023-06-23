
    let colors = ['#F0F4C3','#BDBDBD','#8BC34A','#CDDC39','#AFB42B','#B3E5FC','#F5F5F5','#DCEDC8','#03A9F4','#FFF9C4'];
    let color = colors[Math.ceil(Math.random()*10)];
    // Получение контекста для рисования
    let canvas = window.document.querySelector('canvas');
    let context = canvas.getContext('2d');
    // Функции
    const createLineChart = (xData, yData) => {
    let data = {
    labels: xData,
    datasets: [{
    label: /*[[${dashboardParam}]]*/ 'Показатель',
    data: yData,
    pointStyle: 'star',
    fill: {
    target: 'origin',
    above: color
},
    borderWidth: 1
}]
}
    let config = {
    type: 'line',
    data: data
}
    let chart = new Chart(context, config);
}
    // Получение данных с сервера
    //axios.get('https://www.alphavantage.co/query?function=ALUMINUM&interval=monthly&apikey=demo')

    axios.get('/api/dashboard/V2/'+[[${dashboardParamId}]]+'/'+[[${periodValue}]])
    .then((response)=>{
    let data = response.data;
    let xData = [];
    let yData = [];
    for(let i = 0; i < data.length; i++){
    xData.push(data[i].date);
    yData.push(data[i].value);
}
    createLineChart(xData, yData);
});
    // _____________________________________________________________________________________________

