<template>
  <div class="app-container">
    <div class="dashboard-container">
      <h1 class="dashboard-title">计算机科学技术学院数据中心大屏</h1>
      
      <div class="dashboard-row">
        <!-- 教师科研成果统计 -->
        <div class="dashboard-card">
          <h2 class="card-title">教师科研成果统计</h2>
          <div class="chart-container">
            <el-button type="primary" size="mini" @click="refreshResearchData" style="margin-bottom: 10px">刷新数据</el-button>
            <div id="researchChart" ref="researchChart" class="chart"></div>
          </div>
        </div>
        
        <!-- 横向项目经费到账情况 -->
        <div class="dashboard-card">
          <h2 class="card-title">横向项目经费到账情况</h2>
          <div class="chart-container">
            <el-button type="primary" size="mini" @click="refreshFundingData" style="margin-bottom: 10px">刷新数据</el-button>
            <div id="fundingChart" ref="fundingChart" class="chart"></div>
          </div>
        </div>
      </div>
      
      <div class="dashboard-row">
        <!-- 学生学业情况 -->
        <div class="dashboard-card">
          <h2 class="card-title">学生学业情况</h2>
          <div class="chart-container">
            <el-button type="primary" size="mini" @click="refreshAcademicData" style="margin-bottom: 10px">刷新数据</el-button>
            <div id="academicChart" ref="academicChart" class="chart"></div>
          </div>
        </div>
        
        <!-- 生源分布 -->
        <div class="dashboard-card">
          <h2 class="card-title">生源分布</h2>
          <div class="chart-container">
            <el-button type="primary" size="mini" @click="refreshSourceData" style="margin-bottom: 10px">刷新数据</el-button>
            <div id="sourceChart" ref="sourceChart" class="chart"></div>
          </div>
        </div>
      </div>
      
      <div class="dashboard-row">
        <!-- 开课情况 -->
        <div class="dashboard-card full-width">
          <h2 class="card-title">开课情况</h2>
          <div class="chart-container">
            <el-button type="primary" size="mini" @click="refreshCourseData" style="margin-bottom: 10px">刷新数据</el-button>
            <div id="courseChart" ref="courseChart" class="chart"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: "Dashboard",
  data() {
    return {
      // 图表实例
      researchChart: null,
      fundingChart: null,
      academicChart: null,
      sourceChart: null,
      courseChart: null,
      // 图表数据
      researchData: {
        categories: ['2023', '2024', '2025', '2026'],
        ei: [12, 15, 18, 20],
        sci: [8, 10, 12, 15]
      },
      fundingData: {
        categories: ['2023', '2024', '2025', '2026'],
        amount: [150, 200, 250, 300]
      },
      academicData: {
        categories: ['优秀', '良好', '中等', '及格', '不及格'],
        count: [80, 120, 100, 50, 20]
      },
      sourceData: {
        data: [
          { name: '北京市', value: 100 },
          { name: '上海市', value: 80 },
          { name: '广东省', value: 70 },
          { name: '浙江省', value: 60 },
          { name: '江苏省', value: 50 },
          { name: '其他', value: 200 }
        ]
      },
      courseData: {
        categories: ['计算机科学与技术', '软件工程', '人工智能', '数据科学', '网络工程'],
        required: [10, 12, 8, 9, 11],
        elective: [5, 6, 4, 5, 4]
      }
    };
  },
  mounted() {
    this.initCharts();
  },
  beforeUnmount() {
    // 销毁图表实例
    if (this.researchChart) {
      this.researchChart.dispose();
    }
    if (this.fundingChart) {
      this.fundingChart.dispose();
    }
    if (this.academicChart) {
      this.academicChart.dispose();
    }
    if (this.sourceChart) {
      this.sourceChart.dispose();
    }
    if (this.courseChart) {
      this.courseChart.dispose();
    }
  },
  methods: {
    // 初始化图表
    initCharts() {
      this.initResearchChart();
      this.initFundingChart();
      this.initAcademicChart();
      this.initSourceChart();
      this.initCourseChart();
    },
    
    // 教师科研成果统计图表
    initResearchChart() {
      this.researchChart = echarts.init(this.$refs.researchChart);
      const option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          data: ['EI', 'SCI']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: this.researchData.categories
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: 'EI',
            type: 'bar',
            data: this.researchData.ei,
            itemStyle: {
              color: '#409EFF'
            }
          },
          {
            name: 'SCI',
            type: 'bar',
            data: this.researchData.sci,
            itemStyle: {
              color: '#67C23A'
            }
          }
        ]
      };
      this.researchChart.setOption(option);
    },
    
    // 横向项目经费到账情况图表
    initFundingChart() {
      this.fundingChart = echarts.init(this.$refs.fundingChart);
      const option = {
        tooltip: {
          trigger: 'axis',
          formatter: '{b}年: {c} 万元'
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: this.fundingData.categories
        },
        yAxis: {
          type: 'value',
          axisLabel: {
            formatter: '{value} 万元'
          }
        },
        series: [
          {
            data: this.fundingData.amount,
            type: 'line',
            smooth: true,
            itemStyle: {
              color: '#E6A23C'
            },
            areaStyle: {
              color: {
                type: 'linear',
                x: 0,
                y: 0,
                x2: 0,
                y2: 1,
                colorStops: [{
                  offset: 0, color: 'rgba(230, 162, 60, 0.5)'
                }, {
                  offset: 1, color: 'rgba(230, 162, 60, 0.1)'
                }]
              }
            }
          }
        ]
      };
      this.fundingChart.setOption(option);
    },
    
    // 学生学业情况图表
    initAcademicChart() {
      this.academicChart = echarts.init(this.$refs.academicChart);
      const option = {
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          data: this.academicData.categories
        },
        series: [
          {
            name: '学业情况',
            type: 'pie',
            radius: '50%',
            center: ['50%', '50%'],
            data: this.academicData.categories.map((item, index) => {
              return {
                value: this.academicData.count[index],
                name: item
              };
            }),
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      this.academicChart.setOption(option);
    },
    
    // 生源分布图表
    initSourceChart() {
      this.sourceChart = echarts.init(this.$refs.sourceChart);
      const option = {
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          data: this.sourceData.data.map(item => item.name)
        },
        series: [
          {
            name: '生源分布',
            type: 'pie',
            radius: '50%',
            center: ['50%', '50%'],
            data: this.sourceData.data,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      this.sourceChart.setOption(option);
    },
    
    // 开课情况图表
    initCourseChart() {
      this.courseChart = echarts.init(this.$refs.courseChart);
      const option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          data: ['必修课', '选修课']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: this.courseData.categories
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '必修课',
            type: 'bar',
            data: this.courseData.required,
            itemStyle: {
              color: '#409EFF'
            }
          },
          {
            name: '选修课',
            type: 'bar',
            data: this.courseData.elective,
            itemStyle: {
              color: '#67C23A'
            }
          }
        ]
      };
      this.courseChart.setOption(option);
    },
    
    // 刷新科研数据
    refreshResearchData() {
      // 模拟数据刷新
      this.researchData = {
        categories: ['2023', '2024', '2025', '2026'],
        ei: [12, 15, 18, 22],
        sci: [8, 10, 12, 16]
      };
      this.initResearchChart();
      this.$message.success('科研数据刷新成功');
    },
    
    // 刷新经费数据
    refreshFundingData() {
      // 模拟数据刷新
      this.fundingData = {
        categories: ['2023', '2024', '2025', '2026'],
        amount: [150, 200, 250, 320]
      };
      this.initFundingChart();
      this.$message.success('经费数据刷新成功');
    },
    
    // 刷新学业数据
    refreshAcademicData() {
      // 模拟数据刷新
      this.academicData = {
        categories: ['优秀', '良好', '中等', '及格', '不及格'],
        count: [85, 125, 95, 45, 15]
      };
      this.initAcademicChart();
      this.$message.success('学业数据刷新成功');
    },
    
    // 刷新生源数据
    refreshSourceData() {
      // 模拟数据刷新
      this.sourceData = {
        data: [
          { name: '北京市', value: 110 },
          { name: '上海市', value: 85 },
          { name: '广东省', value: 75 },
          { name: '浙江省', value: 65 },
          { name: '江苏省', value: 55 },
          { name: '其他', value: 190 }
        ]
      };
      this.initSourceChart();
      this.$message.success('生源数据刷新成功');
    },
    
    // 刷新开课数据
    refreshCourseData() {
      // 模拟数据刷新
      this.courseData = {
        categories: ['计算机科学与技术', '软件工程', '人工智能', '数据科学', '网络工程'],
        required: [11, 13, 9, 10, 12],
        elective: [6, 7, 5, 6, 5]
      };
      this.initCourseChart();
      this.$message.success('开课数据刷新成功');
    }
  }
};
</script>

<style scoped>
.dashboard-container {
  padding: 20px;
}

.dashboard-title {
  text-align: center;
  color: #303133;
  margin-bottom: 30px;
  font-size: 24px;
  font-weight: bold;
}

.dashboard-row {
  display: flex;
  margin-bottom: 20px;
  gap: 20px;
}

.dashboard-card {
  flex: 1;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  padding: 20px;
}

.dashboard-card.full-width {
  flex: 100%;
}

.card-title {
  font-size: 16px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 15px;
  text-align: center;
}

.chart-container {
  position: relative;
}

.chart {
  width: 100%;
  height: 300px;
}

@media screen and (max-width: 768px) {
  .dashboard-row {
    flex-direction: column;
  }
  
  .chart {
    height: 250px;
  }
}
</style>
