<template>
  <div>
    <div class="container">
      <div style="marin-bottom: 50px">
        <div v-if="type !== 'user'">
          <h2>用户信息</h2>
          <div style="padding: 50px">
            <div class="row">
              <div class="col-md-6" style="margin-left: 100px">
                <p>姓名：{{ info.nickName }}</p>
                <p>性别：{{ info.sex }}</p>
                <p>出生日期：{{ info.birthday }}</p>
                <p>身高：{{ info.height }}cm</p>
                <p>体重：{{ info.weight }}kg</p>
                <p>肺活量：{{ info.lungCapacity }}ml</p>
              </div>
              <div class="col-md-6">
                <p>身份证号：{{ info.userName }}</p>
                <p>立定跳远：{{ info.standingLongJumpDistance }}cm</p>
                <p>仰卧起坐：{{ info.sitUpsCount }}个</p>
                <p>50米：{{ info.sprintFiftymTime }}秒</p>
                <p>1000米：{{ info.sprintThousandmTime }}秒</p>
                <p>坐位体前屈：{{ info.sitAndReachDistance }}cm</p>
              </div>
            </div>
          </div>
        </div>
        <div style="margin-top: 50px">
          <h2 style="margin-bottom: 50px">用户统计</h2>
          <div class="chart-row">
            <div ref="bloodPressureChart" class="chart"></div>
            <div ref="glucoseChart" class="chart"></div>
          </div>
          <div class="chart-row">
            <div ref="temperatureChart" class="chart"></div>
            <div ref="pulseChart" class="chart"></div>
          </div>
        </div>

        <div
          v-if="type !== 'user'"
          style="margin-top: 50px; text-align: center"
        >
          <div>
            <el-input
              type="textarea"
              v-model="diagnosisContent"
              :rows="6"
              placeholder="请输入分析建议"
              maxlength="500"
              style="width: 80%; resize: none"
            ></el-input>
          </div>
          <el-button type="primary" style="margin-top: 20px" @click="submit"
            >提交</el-button
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from "echarts";

import request from "@/utils/request";

export default {
  data() {
    return {
      type: "user",
      diagnosisContent: "",
      info: {
        id: null,
        userId: null,
        userName: null,
        sex: null,
        birthday: null,
        height: null,
        weight: null,
        lungCapacity: null,
        standingLongJumpDistance: null,
        sitUpsCount: null,
        sprintFiftymTime: null,
        sprintThousandmTime: null,
        sitAndReachDistance: null,
      },
    };
  },
  mounted() {
    request
      .get("/statistics", {
        params: {
          userId: this.$route.query.userId,
        },
      })
      .then((res) => {
        if (res.code === "0") {
          const statData = res.data;
          this.renderBloodPressureChart(
            statData ? statData.bloodPressureData : undefined
          );
          this.renderGlucoseChart(statData ? statData.glucoseData : undefined);
          this.renderTemperatureChart(
            statData ? statData.temperatureData : undefined
          );
          this.renderPulseChart(statData ? statData.pulseData : undefined);
        } else {
          this.$message({
            type: "error",
            message: res.msg,
          });
        }
      });
  },
  created() {
    this.type = this.$route.query.type;
    if (this.type !== "user") {
      this.initInfo();
    }
  },
  methods: {
    initInfo() {
      request
        .get("/physicalFunctions/detail", {
          params: {
            userId: this.$route.query.userId,
          },
        })
        .then((res) => {
          if (res.code === "0") {
            this.info = res.data;
          } else {
            this.$message({
              type: "error",
              message: res.msg,
            });
          }
        });
    },
    generateRandomData() {
      return Array.from(
        { length: 7 },
        () => Math.floor(Math.random() * 30) + 70
      );
    },
    renderBloodPressureChart(statisticData) {
      const chart = echarts.init(this.$refs.bloodPressureChart);
      const systolicData = statisticData
        ? statisticData["systolicData"]
          ? statisticData["systolicData"].map((item) => item.value)
          : []
        : [];

      const diastolicData = statisticData
        ? statisticData["diastolicData"]
          ? statisticData["diastolicData"].map((item) => item.value)
          : []
        : [];

      chart.setOption({
        title: {
          text: "一周内血压变化",
          left: "center",
        },
        legend: {
          data: ["收缩压", "舒张压"],
          top: 30,
        },
        xAxis: {
          type: "category",
          data: statisticData
            ? statisticData["systolicData"]
              ? statisticData["systolicData"].map((item) => item.label)
              : []
            : [],
        },
        yAxis: {
          type: "value",
        },
        tooltip: {
          trigger: "axis",
          formatter: function (params) {
            return (
              params[0].name +
              "<br />" +
              params[0].seriesName +
              ": " +
              params[0].value +
              "<br />" +
              params[1].seriesName +
              ": " +
              params[1].value
            );
          },
        },
        series: [
          {
            data: systolicData,
            type: "line",
            name: "收缩压",
            itemStyle: {
              color: "blue", // 可以设置不同的颜色
            },
          },
          {
            data: diastolicData,
            type: "line",
            name: "舒张压",
            itemStyle: {
              color: "green", // 可以设置不同的颜色
            },
          },
        ],
      });
    },

    renderGlucoseChart(glucoseData) {
      const chart = echarts.init(this.$refs.glucoseChart);
      const data = glucoseData ? glucoseData.map((item) => item.value) : [];
      chart.setOption({
        title: {
          text: "一周内血糖水平变化",
          left: "center",
        },
        xAxis: {
          type: "category",
          data: glucoseData ? glucoseData.map((item) => item.label) : [],
        },
        yAxis: {
          type: "value",
        },
        tooltip: {
          trigger: "axis",
          formatter: function (params) {
            return (
              params[0].name +
              "<br />" +
              params[0].seriesName +
              ": " +
              params[0].value
            );
          },
        },
        series: [
          {
            data: data,
            type: "line",
            name: "血糖水平",
          },
        ],
      });
    },
    renderTemperatureChart(temperatureData) {
      const chart = echarts.init(this.$refs.temperatureChart);
      const data = temperatureData
        ? temperatureData.map((item) => item.value)
        : [];
      chart.setOption({
        title: {
          text: "一周内体温变化",
          left: "center",
        },
        xAxis: {
          type: "category",
          data: temperatureData
            ? temperatureData.map((item) => item.label)
            : [],
        },
        yAxis: {
          type: "value",
        },
        tooltip: {
          trigger: "axis",
          formatter: function (params) {
            return (
              params[0].name +
              "<br />" +
              params[0].seriesName +
              ": " +
              params[0].value
            );
          },
        },
        series: [
          {
            data: data,
            type: "line",
            name: "体温",
          },
        ],
      });
    },
    renderPulseChart(pulseData) {
      const chart = echarts.init(this.$refs.pulseChart);
      const data = pulseData ? pulseData.map((item) => item.value) : [];
      chart.setOption({
        title: {
          text: "一周内脉搏率变化",
          left: "center",
        },
        xAxis: {
          type: "category",
          data: pulseData ? pulseData.map((item) => item.label) : [],
        },
        yAxis: {
          type: "value",
        },
        tooltip: {
          trigger: "axis",
          formatter: function (params) {
            return (
              params[0].name +
              "<br />" +
              params[0].seriesName +
              ": " +
              params[0].value
            );
          },
        },
        series: [
          {
            data: data,
            type: "line",
            name: "脉搏率",
          },
        ],
      });
    },
    submit() {
      console.log(this.diagnosisContent);
      const data = {
        userId: this.$route.query.userId,
        diagnosisContent: this.diagnosisContent,
      };
      request.post("/doctorSuggest/saveSuggest", data).then((res) => {
        if (res.code === "0") {
          this.$message({
            type: "success",
            message: "保存成功",
          });
          this.diagnosisContent = "";
        } else {
          this.$message({
            type: "error",
            message: res.msg,
          });
        }
      });
    },
  },
};
</script>

<style scoped>
.container {
  min-height: 730px;
  background-color: white;
  margin: 20px;
  padding: 50px;

  border-radius: 5px;
}
.chart-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.chart {
  width: 48%;
  height: 300px;
}

.row {
  display: flex;
  justify-content: space-between;
}

.col-md-6 {
  width: 60%;
}

.col-md-6 > p {
  padding: 10px;
}
</style>
