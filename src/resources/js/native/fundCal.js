/**
 * Created by wahsonleung on 15/4/16.
 */
$(document).ready(function () {

    var  displayGrid = function(display){
        var toTop = display ? '' : '10000px';
        $('#fundGrid').css('margin-top',toTop);
    }
    var displayLoadingMask = function(display){
        if(display){
            $('#loadingMask').show();
        } else {
            $('#loadingMask').hide();
        }
    }
    var validateInput = function (target) {
        if($.fn.validatebox){
            var $invalidElem = $(target).find('input.validatebox-invalid');
            $invalidElem.filter(':not(:disabled):first').focus();
            return $invalidElem.length == 0;
        }
        return true;
    }

    var listFunds = function(){
        setTimeout(function(){
            var isLoadRealValuation = $('#toggleRtv').data('isLoadRealValuation');
            console.log(isLoadRealValuation);

            if(isLoadRealValuation){

            } else {

            }
            //TODO
            var fundsStr = fundAppService.listFunds();
            var fundsObj = JSON.parse(fundsStr);
            var funds = fundsObj.funds;
            if(Array.isArray(funds) && funds.length > 0){
                $('#noFundNotice').hide();
                displayGrid(true);

                var totalEarning = 0,
                    totalEbr = 0,
                    totalSum = 0;
                $.each(funds,function(ind,fund){
                    fund.sum = fund.latestNet * fund.share;
                    fund.earningBeforeRedemption = fund.sum - fund.buyCosts;
                    fund.earning = fund.earningBeforeRedemption - fund.sum * fund.redemptionRate;
                    fund.netDiff = fund.latestNet - fund.buyNet;
                    totalEarning += fund.earning;
                    totalEbr += fund.earningBeforeRedemption;
                    totalSum += fund.sum;
                    fund.sum = fund.sum.toFixed(2);
                    fund.earningBeforeRedemption = fund.earningBeforeRedemption.toFixed(2);
                    fund.earning = fund.earning.toFixed(2);
                    fund.netDiff = fund.netDiff.toFixed(4);
                    fund.buyDate = buyDateFormatter(fund.buyDate);
                    fund.netDiff = netDiffFormatter(fund.netDiff);
                    //fund.latestNet = latestNetFormatter(fund.latestNet);
                });
                funds.push({
                    earning:'<span style="color:red">'+totalEarning.toFixed(2)+'</span>',
                    earningBeforeRedemption:totalEbr.toFixed(2),
                    sum:totalSum.toFixed(2)
                });
                showFunds2Table(funds);
            } else {
                displayGrid(false);
                $('#noFundNotice').show();
            }
            displayLoadingMask(false);
        },300);

    }

    var latestNetFormatter = function(lastestNet,netDate){
        return '<span title="' + netDate + '">' + lastestNet +'</span>';
    }
    var buyDateFormatter = function(buyDate){
        return '<span title="' + dayDiff(buyDate,Date.now()) + '">' + buyDate +'</span>';
    }

    var netDiffFormatter = function(netDiff){
        var color = netDiff > 0 ? 'red':'green';
        return '<span style="color:' + color + '">' + netDiff +'</span>';
    }

    var dayDiff = function(oldDay,now){
        var buyDate = new Date(oldDay);
        var dayInterval = (now.valueOf() - buyDate.valueOf()) / 1000 / 3600 / 24;
        return Math.floor(dayInterval)+'天';
    }

    var showFunds2Table = function(data){
        $('#fundTable').datagrid('loadData',data);
    }

    var getInputFundData = function(){
        var form = {};
        $('#fundForm input[type=text]').each(function(ind){
            var field = $(this).attr('name');
            var value = $(this).val();
            form[field] = value;
        });

        return form;
    }

    $('#toggleRtv').change(function(){
        var checked = $(this).prop('checked');
        $('#toggleRtv').data('isLoadRealValuation',checked);
    });

    $('#fundTable').datagrid({
        columns: [[
            {field: 'fundName', title: '基金', width:"22%",align: 'center'},
            {field: 'fundCode', title: 'Name', width:"0", align: 'center'},
            {field: 'latestNet', title: '最新净值', width:"8%", align: 'center',sortable:true},
            {field: 'buyNet', title: '买入净值', width:"6%", align: 'center'},
            {field: 'netDiff', title: '<span style="color:red;">↑</span><span style="color:green;">↓</span>', width:"5%", align: 'center',sortable:true},
            {field: 'buyDate', title: '买入时间', width:"8%", align: 'center'},
            {field: 'share', title: '份额', width:"6%", align: 'center',sortable:true},
            {field: 'buyCosts', title: '花费总额', width:"6%", align: 'center',sortable:true},
            {field: 'buyingRate', title: '购买费率', width:"6%", align: 'center'},
            {field: 'redemptionRate', title: '赎回费率', width:"6%", align: 'center'},
            {field: 'earningBeforeRedemption', title: '净收益<br/>（不含赎回费）', width:"9%", align: 'center',sortable:true},
            {field: 'earning', title: '净收益', width:"8%", align: 'center'},
            {field: 'sum', title: '总额', width:"8%", align: 'center',sortable:true}
        ]],
        rownumbers:true,
        fitColumns:true,
        sortName:'netDiff',
        singleSelect:true,
        ctrlSelect:true
    }).datagrid('hideColumn', 'fundCode');

    $('#dd').dialog({
        title: '添加基金',
        width: 500,
        height: 530,
        closed: true,
        cache: false,
        modal: true,
        resizable: false,
        draggable: false,
        href: 'addFundDialog.html',
        buttons:[{
            text:"I'm the fxcking Btn",
            handler:function(){

                if(validateInput('#fundForm')){
                    displayLoadingMask(true);
                    var form = getInputFundData();
                    var result = fundAppService.addFund(JSON.stringify(form));
                    listFunds();
                    $('#dd').dialog('close');

                }
            }
        }]
    });

    //open add fund dialog
    $("#addFundBtn").click(function (e) {
        $('#dd').dialog('open');
    });

    $('#refreshBtn').click(function(e){
        displayLoadingMask(true);
        listFunds();

    });
    //setTimeout(listFunds,500);
    displayLoadingMask(false);
    //$('#noFundNotice').show();
});

