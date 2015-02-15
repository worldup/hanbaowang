<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<ul>
    <li>
        <ul>
            <li>
                <span class="serialNo" style="color: #00AF00;font-style: oblique">A01:</span>
                <span class="title">保险箱中零用金金额正确</span>
                <span class="remark"   style="color:  #666666;font-style: italic">
                    ( 1.检查保险箱中的零用金，零用金+借款单+支出凭证=公司财务核定金额 2.与电脑中的零用金金额进行核对，应与实际保持一致)
                </span>
            </li>
            <li>
                <br>
            </li>
            <li>
                                <span style="margin-left: 30px">
                                    <input type="radio" name="A01_radio" value="Y" checked="checked"/><span
                                        style="display:inline-block;width: 50px">Y</span>
                                    <input type="radio" name="A01_radio" value="N"/><span
                                        style="display:inline-block;width: 50px">N</span>
                                    <label>问题描述：</label>  <input style="width:500px" type="text" name="A01_text"
                                                                       maxlength="200"/>
                                </span>
                <br>
            </li>
        </ul>

    </li>
    <li>
        <ul>
            <li>
                <span class="serialNo" style="color: #00AF00;font-style: oblique">A02:</span>
                <span class="title">保险箱备用金金额正确</span>
                <span class="remark" style="color:  #666666;font-style: italic">
                    (1.检查保险箱中的备用金与公司核定金额相符 2.备用金不可挪作他用)
                </span>
            </li>
            <li>
                <br>
            </li>
            <li>
                                    <span style="margin-left: 30px">
                                    <input type="radio" name="A02_radio" value="Y" checked="checked"/><span
                                            style="display:inline-block;width: 50px">Y</span>
                                    <input type="radio" name="A02_radio" value="N"/><span
                                            style="display:inline-block;width: 50px">N</span>
                                    <label>问题描述：</label>  <input style="width:500px" type="text" name="A02_text"
                                                                       maxlength="200"/>
                                </span>
                <br>
            </li>
        </ul>
    </li>
    <li>
        <ul>
            <li>
                <span class="serialNo"style="color: #00AF00;font-style: oblique">A03:</span>
                <span class="title">“现金资料管理袋”所有转桌记录真实</span>
                <span class="remark" style="color:  #666666;font-style: italic">
                    (1.以桌为单位将每台收银机的原始转桌单及各种原始收银凭证归类存放，包括：转桌单、非现金支付凭证、打折交易凭证、Refund交易凭证、（上转桌单的）优惠券凭证
                    2.转桌记录与各项凭证一一对应，如有差异，应填写差异说明或相应行动记录
                    3.如实反映实际现金差异，如超出范围，应及时采取相应措施并留档)
                </span>
            </li>
            <li>
                <br>
            </li>
            <li>
                                    <span style="margin-left: 30px">
                                    <input type="radio" name="A03_radio" value="Y" checked="checked"/><span
                                            style="display:inline-block;width: 50px">Y</span>
                                    <input type="radio" name="A03_radio" value="N"/><span
                                            style="display:inline-block;width: 50px">N</span>
                                    <label>问题描述：</label>  <input style="width:500px" type="text" name="A03_text"
                                                                       maxlength="200"/>
                                </span>
                <br>
            </li>
        </ul>
    </li>
    <li>
        <ul>
            <li>
                <span class="serialNo" style="color: #00AF00;font-style: oblique">A04:</span>
                <span class="title">原物料盘存表正确使用且记录完整</span>
                <span  class="remark"  style="color:  #666666;font-style: italic">
                    (1.至少抽查5个品项，实际存货数、盘存记录、MenuLink数据保持一致)
                </span>
            </li>
            <li>
                <br>
            </li>
            <li>
                                    <span style="margin-left: 30px">
                                    <input type="radio" name="A04_radio" value="Y" checked="checked"/><span
                                            style="display:inline-block;width: 50px">Y</span>
                                    <input type="radio" name="A04_radio" value="N"/><span
                                            style="display:inline-block;width: 50px">N</span>
                                    <label>问题描述：</label>  <input style="width:500px" type="text" name="A04_text"
                                                                       maxlength="200"/>
                                </span>
            </li>
        </ul>
    </li>

</ul>
<script type="text/javascript">
    $(document).ready(function(){
        $("input[type=radio]").click(function(){
            var _radio=$(this);
            var _radio_grade=( $("#"+_radio.attr("name")+"_grade"));
            var _radio_origin_grade=_radio.siblings("label").first().children(".grade").first().text();
            if(_radio.val()=='Y'){
                _radio_grade.text(_radio_origin_grade);
            }
            else{
                _radio_grade.text(0);
            }
        })
    })

</script>