<template>
  <div>
    <div class="container">
      <div style="padding: 10px">
        <!-- 功能区域 -->
        <div style="margin: 10px 0">
          <el-button type="primary" @click="add">登记</el-button>
          <router-link
            style="color: #409eff; margin-left: 10px"
            to="/userHealthMeasurements/doctorSuggest"
          >
            <el-button type="success" plain>查看医生建议</el-button>
          </router-link>
        </div>

        <el-table
          v-loading="loading"
          :data="tableData"
          border
          stripe
          style="width: 100%"
        >
          <el-table-column type="index" label="序号" width="50" align="center">
          </el-table-column>
          <el-table-column
            prop="systolicPressure"
            label="收缩压"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="diastolicPressure"
            label="舒张压"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="bloodSugarLevel"
            label="血糖水平"
            align="center"
          >
          </el-table-column>
          <el-table-column prop="temperature" label="体温" align="center">
          </el-table-column>
          <el-table-column prop="pulseRate" label="脉搏率" align="center">
          </el-table-column>
          <el-table-column
            prop="createTime"
            label="登记时间"
            width="200"
            align="center"
          >
          </el-table-column>
          <el-table-column label="操作" width="400" align="center">
            <template #default="scope">
              <el-button
                v-if="scope.row.diagnosisContent"
                size="mini"
                type="primary"
                plain
                @click="handleEdit(scope.row)"
                >编辑</el-button
              >
              <el-popconfirm
                title="确定删除吗？"
                style="margin-left: 10px"
                @confirm="handleDelete(scope.row.id)"
              >
                <template #reference>
                  <el-button size="mini" type="danger">删除</el-button>
                </template>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>

        <div style="margin: 10px 0; text-align: right">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[5, 10, 20]"
            :page-size="pageSize"
            layout="prev, pager, next, sizes,total"
            :total="total"
          ></el-pagination>
        </div>

        <el-dialog
          :title="dialogTitle"
          :visible.sync="dialogVisible"
          width="50%"
        >
          <el-form :model="form" :rules="rules" label-width="120px">
            <el-form-item label="收缩压" prop="systolicPressure">
              <el-input
                v-model="form.systolicPressure"
                placeholder="正整数"
                style="width: 80%"
              ></el-input>
            </el-form-item>
            <el-form-item label="舒张压" prop="diastolicPressure">
              <el-input
                v-model="form.diastolicPressure"
                placeholder="正整数"
                style="width: 80%"
              ></el-input>
            </el-form-item>
            <el-form-item label="血糖水平" prop="bloodSugarLevel">
              <el-input
                v-model="form.bloodSugarLevel"
                placeholder="（精确到小数点后两位）"
                style="width: 80%"
              ></el-input>
            </el-form-item>
            <el-form-item label="体温" prop="temperature">
              <el-input
                v-model="form.temperature"
                placeholder="（精确到小数点后两位）"
                style="width: 80%"
              ></el-input>
            </el-form-item>
            <el-form-item label="脉搏率" prop="pulseRate">
              <el-input
                v-model="form.pulseRate"
                placeholder="正整数"
                style="width: 80%"
              ></el-input>
            </el-form-item>
          </el-form>
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="dialogVisible = false">取消</el-button>
              <el-button type="primary" @click="save">确定</el-button>
            </span>
          </template>
        </el-dialog>
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
        id: "",
        systolicPressure: "",
        diastolicPressure: "",
        bloodSugarLevel: "",
        temperature: "",
        pulseRate: "",
      },
      dialogVisible: false,
      search: "",
      currentPage: 1,
      pageSize: 10,
      dialogTitle: "添加",
      total: 0,
      tableData: [],
      categories: [],
      startTime: "",
      endTime: "",
      rules: {
        systolicPressure: [
          { required: true, message: "收缩压不能为空", trigger: "blur" },
        ],
        diastolicPressure: [
          { required: true, message: "舒张压不能为空", trigger: "blur" },
        ],
        bloodSugarLevel: [
          {
            required: true,
            message: "血糖水平（精确到小数点后两位）不能为空",
            trigger: "blur",
          },
        ],
        temperature: [
          {
            required: true,
            message: "体温（精确到小数点后两位）不能为空",
            trigger: "blur",
          },
        ],
        pulseRate: [
          { required: true, message: "脉搏率不能为空", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    init() {
      request
        .get("/healthMeasurements/userPageList", {
          params: {
            pageNum: this.currentPage,
            pageSize: this.pageSize,
            search: this.search,
          },
        })
        .then((res) => {
          if (res.code === "0") {
            this.tableData = res.data.records;
            this.total = res.data.total;
            this.loading = false;
          } else {
            this.$message({
              type: "error",
              message: res.msg,
            });
          }
        });
    },
    add() {
      this.dialogTitle = "添加";
      this.form = {
        id: undefined,
        systolicPressure: undefined,
        diastolicPressure: undefined,
        bloodSugarLevel: undefined,
        temperature: undefined,
        pulseRate: undefined,
      };
      this.dialogVisible = true;
    },
    save() {
      request
        .post("/healthMeasurements/userSaveOrUpdate", this.form)
        .then((res) => {
          if (res.code === "0") {
            this.$message({
              type: "success",
              message: this.dialogTitle === "添加" ? "添加成功" : "修改成功",
            });
            this.init();
            this.dialogVisible = false;
          } else {
            this.$message({
              type: "error",
              message: res.msg,
            });
          }
        });
    },
    handleEdit(row) {
      this.dialogTitle = "修改";
      this.form = row;
      this.dialogVisible = true;
    },
    handleDelete(id) {
      request.post("/healthMeasurements/userRemove", { id: id }).then((res) => {
        if (res.code === "0") {
          this.$message({
            type: "success",
            message: "删除成功",
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
    handleSizeChange(val) {
      this.pageSize = val;
      this.init();
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.init();
    },
  },
  created() {
    this.init();
  },
  mounted() {
    console.log("Component mounted");
    console.log(moment);
    //  moment(this.endTime).format("YYYY-MM-DD HH:mm:ss")
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
</style>
