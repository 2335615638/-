<template>
  <div>
    <div class="container">
      <div style="padding: 10px">
        <div style="margin: 10px 0">
          <el-input
            v-model="search"
            placeholder="请输入关键字"
            style="width: 20%"
            clearable
          ></el-input>
          <el-button type="primary" style="margin-left: 5px" @click="load"
            >查询</el-button
          >
          <router-link
            style="color: #409eff; margin-left: 10px"
            to="/userDoctor/reply"
          >
            <el-button type="success" plain>查看医生回复</el-button>
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
          <el-table-column prop="nickName" label="医生名称" align="center">
          </el-table-column>
          <el-table-column prop="sex" label="性别" align="center">
            <template #default="scope">
              <p v-if="scope.row.sex === '1'">男</p>
              <p v-else>女</p>
            </template>
          </el-table-column>

          <el-table-column label="操作" width="400" align="center">
            <template #default="scope">
              <el-button
                size="mini"
                type="success"
                @click="openDialog(scope.row.id)"
                plain
                >咨询</el-button
              >
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
          width="30%"
          @close="dialogVisible = false"
        >
          <el-form :model="consultationContent">
            <el-form-item label="咨询内容" prop="consultationContent">
              <el-input
                type="textarea"
                :rows="4"
                v-model="consultationContent"
                placeholder="请输入咨询内容"
              ></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="submitConsultation"
              >确认咨询</el-button
            >
          </div>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  data() {
    return {
      loading: true,
      dialogVisible: false,
      dialogTitle: "",
      search: "",
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData: [],
      bookList: [],
      consultationContent: "",
      doctorId: "",
    };
  },
  methods: {
    openDialog(doctorId) {
      this.dialogVisible = true;
      this.dialogTitle = "咨询";
      this.doctorId = doctorId;
      this.consultationContent = ""; // Clear any previous content
    },
    submitConsultation() {
      const data = {
        doctorId: this.doctorId,
        consultationContent: this.consultationContent,
      };
      request.post("/ask/consultingService", data).then((res) => {
        if (res.code === "0") {
          this.$message({
            type: "success",
            message: "咨询成功",
          });
          this.dialogVisible = false;
        } else {
          this.$message({
            type: "error",
            message: res.msg,
          });
        }
      });
      this.dialogVisible = false;
    },
    load() {
      this.loading = true;
      request
        .get("/user/base/doctorPageList", {
          params: {
            pageNum: this.currentPage,
            pageSize: this.pageSize,
            search: this.search,
          },
        })
        .then((res) => {
          this.loading = false;
          this.tableData = res.data.records;
          this.total = res.data.total;
        });
    },

    handleSizeChange(pageSize) {
      // 改变当前每页的个数触发
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum) {
      // 改变当前页码触发
      this.currentPage = pageNum;
      this.load();
    },
  },
  created() {
    this.load();
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

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}
</style>
