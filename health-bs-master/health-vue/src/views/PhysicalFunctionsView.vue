<template>
  <div>
    <div class="container">
      <div style="max-width: 1200px; margin: 0 auto; text-align: center">
        <el-form :model="form" label-width="120px">
          <div class="row">
            <div class="col-md-6">
              <el-form-item label="性别" label-width="100px" prop="sex">
                <el-select
                  v-model="form.sex"
                  placeholder="请选择"
                  style="width: 80%"
                >
                  <el-option label="男" value="男"></el-option>
                  <el-option label="女" value="女"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item
                label="出生日期"
                label-width="100px"
                prop="birthday"
              >
                <el-date-picker
                  v-model="form.birthday"
                  type="date"
                  style="width: 80%"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                ></el-date-picker>
              </el-form-item>
              <el-form-item label="身高(cm)" label-width="100px" prop="height">
                <el-input
                  v-model="form.height"
                  placeholder="请输入身高(cm)"
                  style="width: 80%"
                ></el-input>
              </el-form-item>
              <el-form-item label="体重(kg)" label-width="100px" prop="weight">
                <el-input
                  v-model="form.weight"
                  placeholder="请输入体重(kg)"
                  style="width: 80%"
                ></el-input>
              </el-form-item>
              <el-form-item
                label="肺活量(ml)"
                label-width="100px"
                prop="lungCapacity"
              >
                <el-input
                  v-model="form.lungCapacity"
                  placeholder="请输入肺活量"
                  style="width: 80%"
                ></el-input>
              </el-form-item>
            </div>
            <div class="col-md-6">
              <el-form-item
                label="立定跳远(cm)"
                prop="standingLongJumpDistance"
              >
                <el-input
                  placeholder="请输入立定跳远距离(cm)"
                  v-model="form.standingLongJumpDistance"
                  style="width: 80%"
                ></el-input>
              </el-form-item>
              <el-form-item label="仰卧起坐(个)" prop="sitUpsCount">
                <el-input
                  v-model="form.sitUpsCount"
                  placeholder="请输入仰卧起坐个数"
                  style="width: 80%"
                ></el-input>
              </el-form-item>
              <el-form-item label="50米(秒)" prop="sprintFiftymTime">
                <el-input
                  v-model="form.sprintFiftymTime"
                  placeholder="请输入50米跑时间"
                  style="width: 80%"
                ></el-input>
              </el-form-item>
              <el-form-item label="1000米(秒)" prop="sprintThousandmTime">
                <el-input
                  v-model="form.sprintThousandmTime"
                  placeholder="请输入1000米跑时间"
                  style="width: 80%"
                ></el-input>
              </el-form-item>
              <el-form-item label="坐位体前屈(cm)" prop="sitAndReachDistance">
                <el-input
                  v-model="form.sitAndReachDistance"
                  placeholder="请输入坐位体前屈距离"
                  style="width: 80%"
                ></el-input>
              </el-form-item>
            </div>
          </div>
        </el-form>
        <el-button style="margin-top: 50px" type="primary" @click="save"
          >保存</el-button
        >
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import moment from "moment";

export default {
  data() {
    return {
      loading: true,
      form: {
        sex: "",
        birthday: "",
        height: "",
        weight: "",
        lungCapacity: "",
        standingLongJumpDistance: "",
        sitUpsCount: "",
        sprintFiftymTime: "",
        sprintThousandmTime: "",
        sitAndReachDistance: "",
      },
    };
  },
  methods: {
    init() {
      request.get("/physicalFunctions/mine").then((res) => {
        if (res.code === "0") {
          if (res.data) {
            this.form = {
              sex: res.data.sex || "",
              birthday: res.data.birthday || "",
              height: res.data.height || "",
              weight: res.data.weight || "",
              lungCapacity: res.data.lungCapacity || "",
              standingLongJumpDistance: res.data.standingLongJumpDistance || "",
              sitUpsCount: res.data.sitUpsCount || "",
              sprintFiftymTime: res.data.sprintFiftymTime || "",
              sprintThousandmTime: res.data.sprintThousandmTime || "",
              sitAndReachDistance: res.data.sitAndReachDistance || "",
            };
          }
        } else {
          this.$message({
            type: "error",
            message: res.msg || "Failed to retrieve data",
          });
        }
      });
    },
    save() {
      this.form.birthday = moment(this.form.birthday).format("YYYY-MM-DD");
      request.post("/physicalFunctions/maintenance", this.form).then((res) => {
        if (res.code === "0") {
          this.$message({
            type: "success",
            message: "保存成功",
          });
          this.init();
        } else {
          this.$message({
            type: "error",
            message: res.msg,
          });
        }
      });
    },
  },
  created() {
    this.init();
  },
  mounted() {
    console.log("Component mounted");
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

.row {
  display: flex;
  justify-content: space-between;
}

.col-md-6 {
  width: 60%;
}

.el-form-item {
  margin-bottom: 15px;
}
</style>
