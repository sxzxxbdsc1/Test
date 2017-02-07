# 测试提交
## 2 ##
### 3 ###
#### 4 ####
#### 5 ####
     if (status.size() != 0) {
            if (status.contains("20")) {
                textZhuangTai += "已装车";
                binding.cbZtYzc.setChecked(true);
            } if (status.contains("30")) {
                textZhuangTai += "已卸货";
                binding.cbZtYxh.setChecked(true);
            } if (status.contains("31")) {
                textZhuangTai += "已提票";
                binding.cbZtYth.setChecked(true);
            } if (status.contains("40")) {
                textZhuangTai += "已签收";
                binding.cbZtYqs.setChecked(true);
            }

        }else {
            binding.cbZtAll.setChecked(true);
        }
这是一个测试文件
2017/2/7 10:44:51 

----------
说的