<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<ul>
    <li>
        <ul>
            <li>
                <span class="serialNo" style="color: #00AF00;font-style: oblique">B24:</span>
                <span class="title">员工餐记录完整正确</span>
                <span class="remark"  style="color:  #666666;font-style: italic">
                    ( 1.员工餐/经理餐等O折交易须有值班经理签名，并与“员工餐登记表”对应
                      2.员工餐登记表须有明确的产品数量，及领用员工的签名
                      3.每日员工餐登记表应与收银机小票一同保存，以月汇总归档，餐厅保存1年)
                </span>
            </li>
            <li>
                <br>
            </li>
            <li>
                                <span style="margin-left: 30px">
                                       <label style="color: #ff0000;">分数:<span class='grade'>5</span></label>

                                    <input type="radio" name="B24_radio" value="Y" checked="checked"/><span
                                        style="display:inline-block;width: 50px">Y</span>
                                    <input type="radio" name="B24_radio" value="N"/><span
                                        style="display:inline-block;width: 50px">N</span>
                                      <label style="color: #ff0000;">得分:<span id="B24_radio_grade">5</span></label>
                                    <label>问题描述：</label>  <input style="width:500px" type="text" name="B24_text"
                                                                       maxlength="200"/>
                                </span>
                <br>
            </li>
        </ul>

    </li>
    <li>
        <ul>
            <li>
                <span  class="serialNo" style="color: #00AF00;font-style: oblique">B25:</span>
                <span class="title">损耗记录完整正确</span>
                <span class="remark" style="color:  #666666;font-style: italic">
                    (1.所有因应各种原因未被制作或售卖而丢弃的原物料或产品都应记录损耗
                     2.每个班次结束都应及时结算本班次的损耗记录，并寻找机会点在后续班次中加以控制
                     3.及时录入ML系统
                     4.损耗表以月汇总归档，餐厅保存1年)
                </span>
            </li>
            <li>
                <br>
            </li>
            <li>
                                    <span style="margin-left: 30px">
                                             <label style="color: #ff0000;">分数:<span class='grade'>5</span></label>
                                    <input type="radio" name="B25_radio" value="Y" checked="checked"/><span
                                            style="display:inline-block;width: 50px">Y</span>
                                    <input type="radio" name="B25_radio" value="N"/><span
                                            style="display:inline-block;width: 50px">N</span>
         <label style="color: #ff0000;">得分:<span id="B25_radio_grade">5</span></label>
                                    <label>问题描述：</label>  <input style="width:500px" type="text" name="B25_text"
                                                                       maxlength="200"/>
                                </span>
                <br>
            </li>
        </ul>
    </li>
    <li>
        <ul>
            <li>
                <span class="serialNo" style="color: #00AF00;font-style: oblique">B26:</span>
                <span  class="title">进货记录完整正确</span>
                <span  class="remark" style="color:  #666666;font-style: italic">
                    ( 1.进货记录及时且正确导入MenuLink系统
                      2.及时处理餐厅调拨，Menulink系统中无“未处理调拨单”
                      3.进货单集中管理和保存，以月为单位存档，每季度上交公司财务)</span>
            </li>
            <li>
                <br>
            </li>
            <li>
                                    <span style="margin-left: 30px">
                                           <label style="color: #ff0000;">分数:<span class='grade'>2</span></label>

                                    <input type="radio" name="B26_radio" value="Y" checked="checked"/><span
                                            style="display:inline-block;width: 50px">Y</span>
                                    <input type="radio" name="B26_radio" value="N"/><span
                                            style="display:inline-block;width: 50px">N</span>
                                                 <label style="color: #ff0000;">得分:<span id="B26_radio_grade">2</span></label>
                                    <label>问题描述：</label>  <input style="width:500px" type="text" name="B26_text"
                                                                       maxlength="200"/>
                                </span>
                <br>
            </li>
        </ul>
    </li>
    <li>
        <ul>
            <li>
                <span class="serialNo" style="color: #00AF00;font-style: oblique">B27:</span>
                <span class="title">原物料按存放要求先进先出整齐堆放</span>
                <span class="remark"   style="color:  #666666;font-style: italic">
                    (1.库房定位图有效且被使用 2.物料摆放执行6-2-1原则 3.先进先出，无过期原料)
                </span>
            </li>
            <li>
                <br>
            </li>
            <li>
                                    <span style="margin-left: 30px">
                                           <label style="color: #ff0000;">分数:<span class='grade'>2</span></label>

                                    <input type="radio" name="B27_radio" value="Y" checked="checked"/><span
                                            style="display:inline-block;width: 50px">Y</span>
                                    <input type="radio" name="B27_radio" value="N"/><span
                                            style="display:inline-block;width: 50px">N</span>
                                          <label style="color: #ff0000;">得分:<span id="B27_radio_grade">2</span></label>
                                    <label>问题描述：</label>  <input style="width:500px" type="text" name="B27_text"
                                                                       maxlength="200"/>
                                </span>
            </li>
        </ul>
    </li>
    <li>
        <ul>
            <li>
                <span class="serialNo" style="color: #00AF00;font-style: oblique">B28:</span>
                <span class="title">原物料盘存表正确使用且记录完整</span>
                <span class="remark"   style="color:  #666666;font-style: italic">
                    (1.日周月盘存表手工记录存档保存,且对分差较大品项有及时的跟进行动
                     2.每日早班随机抽5个品项进行复核记录并签字
                     3.OC每月至少1次随机抽查7个品项进行复核记录并签字
                     4.盘存表以月汇总归档，餐厅保存1年)
                </span>
            </li>
            <li>
                <br>
            </li>
            <li>
                                    <span style="margin-left: 30px">
                                           <label style="color: #ff0000;">分数:<span class='grade'>5</span></label>

                                    <input type="radio" name="B28_radio" value="Y" checked="checked"/><span
                                            style="display:inline-block;width: 50px">Y</span>
                                    <input type="radio" name="B28_radio" value="N"/><span
                                            style="display:inline-block;width: 50px">N</span>
                                          <label style="color: #ff0000;">得分:<span id="B28_radio_grade">5</span></label>
                                    <label>问题描述：</label>  <input style="width:500px" type="text" name="B28_text"
                                                                       maxlength="200"/>
                                </span>
            </li>
        </ul>
    </li>
    <li>
        <ul>
            <li>
                <span class="serialNo" style="color: #00AF00;font-style: oblique">B29:</span>
                <span class="title">原物料管理原始资料符合规范</span>
                <span class="remark"  style="color:  #666666;font-style: italic">
                    (1.所有存货管理资料填写使用圆珠笔或水笔，无涂改痕迹，如需修改仍保持原有记录并在修改处有修改人签名)
                </span>
            </li>
            <li>
                <br>
            </li>
            <li>
                                    <span style="margin-left: 30px">
                                           <label style="color: #ff0000;">分数:<span class='grade'>5</span></label>

                                    <input type="radio" name="B29_radio" value="Y" checked="checked"/><span
                                            style="display:inline-block;width: 50px">Y</span>
                                    <input type="radio" name="B29_radio" value="N"/><span
                                            style="display:inline-block;width: 50px">N</span>
                                            <label style="color: #ff0000;">得分:<span id="B29_radio_grade">5</span></label>
                                    <label>问题描述：</label>  <input style="width:500px" type="text" name="B29_text"
                                                                       maxlength="200"/>
                                </span>
            </li>
        </ul>
    </li>

</ul>
