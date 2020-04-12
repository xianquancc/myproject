$(function () {
    $('#input-keywords').setPlaceHolder('请输入搜索关键词');

    window.Util.renderTab(window._CONFIG.TREE, false, {
        '政府信息公开工作年度报告': function (data) {
            if (window._CONFIG.ACTION === 'annualreport') {
                window._CONFIG.curId = data.id;
                $('.gkml-ndbg').addClass('selected');
                $('.side-bar').hide();
                $('#router-content').find('.text').html(data.name);
                $('.table-wrapper').css('width', '100%');
            }
        }
    });

    if (window._CONFIG.ACTION === 'index') {
        $('.gkml-index').addClass('selected');
    }


    Date.prototype.Format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1,                 //月份
            "d+": this.getDate(),                    //日
            "h+": this.getHours(),                   //小时
            "m+": this.getMinutes(),                 //分
            "s+": this.getSeconds(),                 //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds()             //毫秒
        };
        if (/(y+)/.test(fmt))
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    };

    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);//search,查询？后面的参数，并匹配正则
        if (r != null) return unescape(r[2]);
        return null;
    }
    function getArticleJson(id, page) {
        $('.loading').show();
        $('.table-content').hide();
        var url = '';
        if (!id) {
            id = 0;
        }
        url = '/gkmlpt/api/all/' + id + '?page=' + page + '&sid=' + window._CONFIG.SID;
        if (window._CONFIG.ENV !== 'production') {
            url = '/' + window._CONFIG.SID + '/api/all/' + id + '?page=' + page;
        }
        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json',
            success: function (data) {

                $('.loading').hide();
                $('.table-content').show();
                renderTable(data, page);
                $('#contentFrame').height($('.table-wrapper').height() + 100);
            },
            error: function (err) {
                console.log(err);
                $('.loading').hide();
                $('.table-content').show();
                var tbody = $('.table-content tbody');
                tbody.append('<tr><td colspan="4" style="text-align: center;font-size: 14px">暂时没有新内容</td></tr>');
            }
        });
    }

    function renderTable(data, page) {
        var tbody = $('.table-content tbody');
        tbody.empty();
        var articles = data.articles;
        for (var i = 0; i < articles.length; i++) {
            tbody.append(renderTableTR(articles[i]));
        }
        if (!articles.length) {
            tbody.append('<tr><td colspan="4" style="text-align: center;font-size: 14px">暂时没有新内容</td></tr>');
        }
        renderPages(data.total, page);
    }

    var $pop = $('#pop-wrap').on('mouseenter', function () {
        clearTimeout(leaveTimer);
    }).on('mouseleave', function () {
        handleMouseLeave();
    });
    var keys = ['identifier', 'classify_theme_name', 'publisher', 'date', 'title', 'keywords', 'document_number'];
    var enterTimer = null, leaveTimer = null;

    function handleMouseLeave() {
        clearTimeout(enterTimer);
        leaveTimer = setTimeout(function () {
            $pop.fadeOut(100);
            $.each(keys, function (key, value) {
                $pop.find('.' + value).html('');
            });
        }, 200);
    }

    function renderTableTR(data) {
        var tr = $('<tr></tr>');
        var one = Math.floor(data.id / 1000000);
        var two = Math.floor(data.id / 1000);
        var url = data.url;
        if (!data.url) {
            url = 'content/' + one + '/' + two + '/post_' + data.id + '.html';
            url = window._CONFIG.ENV !== 'production' ? '/' + window._CONFIG.SID + url : G.url('gkmlpt', url);
        }
        if (G.request['jump']) {
            if (url.indexOf('?') != -1) {
                url += '&jump=false';
            } else {
                url += '?jump=false';
            }
        }
        if (window._CONFIG.SID == '2' && window._CONFIG.curId <= 4 && window._CONFIG.curId > 0) {
            tr.append('<td  class="first-td"><a href="' + url + '" target="_blank">' + data.title + '</a></td>');
            tr.append('<td></td>');
            tr.append('<td></td>');
            tr.append('<td>' + data.document_number + '</td>');
        } else {
            tr.append('<td  class="first-td"><a href="' + url + '" target="_blank">' + data.title + '</a></td>');
            tr.append('<td>' + new Date(data.create_time * 1000).Format("yyyy-MM-dd") + '</td>');
            tr.append('<td>' + new Date(data.date * 1000).Format("yyyy-MM-dd") + '</td>');
            tr.append('<td>' + data.document_number + '</td>');
        }
        tr.on('mouseenter', function () {
            enterTimer = setTimeout(function () {
                $.each(keys, function (key, value) {
                    var temp = data[value];
                    if (value === 'first_publish_time') {
                        temp = moment.unix(data['date']).format('YYYY-MM-DD');
                    }
                    $pop.find('.' + value).html(temp);
                });
                $pop.css({
                    left: tr.offset().left + 40 + 'px',
                    top: tr.offset().top + tr.height() - 5 + 'px'
                }).fadeIn(150);
            }, 500);
        }).on('mouseleave', function () {
            handleMouseLeave();
        });
        return tr;
    }

    function renderPages(total, curr) {
        if (total <= 100) {
            $('.pagination').hide();
            $('.footer-warp').css('marginTop', '100px');
        } else {
            $('.footer-warp').css('marginTop', '0px');
            $('.pagination').show();
            $('.pagination').pagination({
                totalData: total,
                showData: 100,
                current: curr,
                prevContent: '上一页',
                nextContent: '下一页',
                keepShowPN: true,
                coping: true,
                callback: function (api) {
                    getArticleJson(window._CONFIG.curId, api.getCurrent());
                }
            });
        }
    }

    function getData() {
        if (window._CONFIG.curId) {
            window.location.href = '#' + window._CONFIG.curId;
        }
        getArticleJson(window._CONFIG.curId, 1);
    }


    var $doc = $(document).on('get-data', function () {
        getData();
    });

    if (!window.location.hash) {
        $doc.trigger('get-data');
    }

});