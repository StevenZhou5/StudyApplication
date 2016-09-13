package test.zhouzhenwu.com.mydemo.common.widgets.chart;

import android.graphics.Color;
import android.support.annotation.ColorInt;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/9/7
 * 类简介：自定义评级流程的趋势折线图中每一流程Item所对应图标上需要的数据
 */
public class ChartItemBean {
    private String levelText; // 矩形框中的文案
    private int levelIndex; // 对应等级在Y轴的索引：1代表Y轴1个单元格高度，2代表Y周2个单元格高度
    @ColorInt
    private int color = Color.BLACK;

    private String stepText; // 底部很坐标的步骤文案

    public String getLevelText() {
        return levelText;
    }

    public void setLevelText(String levelText) {
        this.levelText = levelText;
    }

    public int getLevelIndex() {
        return levelIndex;
    }

    public void setLevelIndex(int levelIndex) {
        this.levelIndex = levelIndex;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getStepText() {
        return stepText;
    }

    public void setStepText(String stepText) {
        this.stepText = stepText;
    }
}
