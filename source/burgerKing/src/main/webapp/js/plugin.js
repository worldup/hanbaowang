/**
 * Created by lili on 15-1-14.
 */
(function($) {
    $.fn.extend({
        operator: function(options) {
            var defaults = {
                domId:'#operator',
                contextPath:'',
                opType:1//默认OM
            };
            var options = $.extend(defaults, options);
            var localCache=[];
            $.post(defaults.contextPath+"/user/loadUser",{role:defaults.opType},function(data){
                var jsonResult=$.parseJSON(data);
                $.each(jsonResult,function(i, value) {
                    localCache.push({ label: value.name, value: value.id })
                });
                $('#storeName').autocomplete({
                    source: localCache
                });
            })

        }
    });
})(jQuery);