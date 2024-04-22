<template>
  <div>
    <div class="container">
      <div style="padding: 10px">
        <div style="margin: 10px 0">
          <el-button type="primary" @click="add">新增</el-button>
        </div>

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
          <el-table-column prop="userName" label="用户名" align="center">
          </el-table-column>
          <el-table-column prop="nickName" label="昵称" align="center">
          </el-table-column>
          <el-table-column prop="sex" label="性别" align="center">
            <template #default="scope">
              <p v-if="scope.row.sex === '1'">男</p>
              <p v-else>女</p>
            </template>
          </el-table-column>
          <el-table-column prop="avatarUrl" label="头像">
            <template #default="scope">
              <img
                :src="scope.row.avatarUrl"
                style="width: 50px; height: 50px"
              />
            </template>
          </el-table-column>
          <el-table-column
            prop="status"
            width="100"
            label="状态"
            align="center"
          >
            <template #default="scope">
              <el-switch
                v-model="scope.row.status"
                :active-value="'1'"
                :inactive-value="'0'"
                @change="handleStatusChange(scope.row)"
              >
              </el-switch>
            </template>
          </el-table-column>

          <el-table-column label="角色" width="300" align="center">
            <template #default="scope">
              <el-select
                v-model="scope.row.role"
                placeholder="请选择"
                style="width: 80%"
                :disabled="true"
              >
                <!-- 写死的选项 -->
                <el-option label="管理员" value="ADMIN"></el-option>
                <el-option label="用户" value="USER"></el-option>
                <el-option label="医生" value="DOCTOR"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="400" align="center">
            <template #default="scope">
              <el-button type="primary" plain @click="handleEdit(scope.row)"
                >编辑</el-button
              >
              <el-popconfirm
                title="确定删除吗？"
                style="margin-left: 10px"
                @confirm="handleDelete(scope.row.id)"
              >
                <template #reference>
                  <el-button type="danger">删除</el-button>
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
          width="600px"
        >
          <el-form :model="form" :rules="rules" label-width="120px">
            <el-form-item label="头像" prop="avatarUrl">
              <div
                style="
                  background-color: #e9e9e9;
                  width: 120px;
                  height: 120px;
                  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                "
              >
                <el-upload
                  class="avatar-uploader"
                  action="/api/common/uploadImage?type=user-avatar"
                  :show-file-list="false"
                  :before-upload="beforeAvatarUpload"
                  :on-success="handleAvatarSuccess"
                >
                  <img
                    v-if="form.avatarUrl"
                    :src="form.avatarUrl"
                    style="width: 120px; height: 120px"
                    class="avatar"
                  />
                  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
              </div>
            </el-form-item>
            <el-form-item label="用户名" prop="userName">
              <el-input v-model="form.userName" style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="昵称" prop="nickName">
              <el-input v-model="form.nickName" style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="角色" prop="role" v-if="dialogTitle == '新增'">
              <el-select
                v-model="form.role"
                placeholder="请选择"
                style="width: 80%"
              >
                <el-option label="管理员" value="ADMIN"></el-option>
                <el-option label="用户" value="USER"></el-option>
                <el-option label="医生" value="DOCTOR"></el-option>
              </el-select>
            </el-form-item>

            <el-form-item prop="sex" label="性别">
              <el-radio-group v-model="form.sex">
                <el-radio label="1">男</el-radio>
                <el-radio label="0">女</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-form>
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="dialogVisible = false">取 消</el-button>
              <el-button type="primary" @click="save">确 定</el-button>
            </span>
          </template>
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
      form: {
        id: "",
        userName: "",
        nickName: "",
        avatarUrl: "",
        age: "",
        sex: "男",
        email: "",
        role: "",
      },
      dialogVisible: false,
      dialogTitle: "",
      search: "",
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData: [],
      bookList: [],
      rules: {
        userName: [
          { required: true, message: "用户名不能为空", trigger: "blur" },
        ],
        nickName: [
          { required: true, message: "昵称不能为空", trigger: "blur" },
        ],
        status: [{ required: true, message: "状态不能为空", trigger: "blur" }],
        sex: [{ required: true, message: "性别不能为空", trigger: "blur" }],
        avatarUrl: [{ required: true, message: "请上传头像", trigger: "blur" }],
      },
    };
  },
  methods: {
    beforeAvatarUpload(file) {
      const maxSize = 1048576; // 1MB = 1048576 bytes
      // 检查文件大小
      if (file.size > maxSize) {
        this.$message.error("文件大小超过允许的最大限制（1MB）");
        return false; // 阻止上传
      }
      return true; // 允许上传
    },
    load() {
      this.loading = true;
      request
        .get("/admin/user/pageList", {
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
    add() {
      this.dialogTitle = "新增";
      this.dialogVisible = true;
      this.form = {
        id: "",
        userName: "",
        nickName: "",
        age: "",
        avatarUrl: "",
        sex: "1",
        email: "",
        role: null,
      };
    },
    save() {
      if (this.form.id) {
        // 更新
        request.post("/admin/user/backSaveOrUpdate", this.form).then((res) => {
          console.log(res);
          if (res.code === "0") {
            this.$message({
              type: "success",
              message: "更新成功",
            });

            this.load(); // 刷新表格的数据
            this.dialogVisible = false; // 关闭弹窗
          } else {
            this.$message({
              type: "error",
              message: res.msg,
            });
          }
        });
      } else {
        // 新增
        request.post("/admin/user/backSaveOrUpdate", this.form).then((res) => {
          console.log(res);
          if (res.code === "0") {
            this.$message({
              type: "success",
              message: "新增成功",
            });
            this.load(); // 刷新表格的数据
            this.dialogVisible = false; // 关闭弹窗
          } else {
            this.$message({
              type: "error",
              message: res.msg,
            });
          }
        });
      }
    },
    handleStatusChange(row) {
      const data = {
        id: row.id,
      };

      // Make a request to the backend to update the status
      request.post("/admin/user/changeStatus", data).then((res) => {
        if (res.code === "0") {
          this.$message.success("状态更新成功");
          this.load();
        } else {
          this.$message.error("状态更新失败");
        }
      });
    },
    handleEdit(row) {
      this.dialogTitle = "修改";
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogVisible = true;
    },
    handleDelete(id) {
      request.post("/admin/user/removeUser", { id: id }).then((res) => {
        if (res.code === "0") {
          this.$message({
            type: "success",
            message: "删除成功",
          });
        } else {
          this.$message({
            type: "error",
            message: res.msg,
          });
        }
        this.load(); // 删除之后重新加载表格的数据
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
    handleAvatarSuccess(res) {
      this.form.avatarUrl = res.data;
      this.$message.success("上传成功");
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
