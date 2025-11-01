import { HardhatRuntimeEnvironment } from "hardhat/types";
import { DeployFunction } from "hardhat-deploy/types";
import { Contract } from "ethers";

const deployKJMToken: DeployFunction = async function (hre: HardhatRuntimeEnvironment) {
  const { deployer } = await hre.getNamedAccounts();
  const { deploy } = hre.deployments;

  // 定义初始供应量（根据合约要求，需包含18位小数）
  // 示例：1000000 * 10^18 表示总供应量为100万枚代币
  const initialSupply = hre.ethers.parseEther("1000000"); // 自动处理18位小数

  // 部署KJMToken合约（合约名称必须与.sol中的合约名一致）
  await deploy("KJMToken", {
    from: deployer,
    // 构造函数仅需要一个参数：initialSupply_（初始供应量）
    args: [initialSupply],
    log: true,
    autoMine: true,
  });

  console.log("Deploying KJMToken...");
  // 验证部署结果
  const kjmToken = await hre.ethers.getContract<Contract>("KJMToken", deployer);
  console.log("✅ 代币名称:", await kjmToken.name());
  console.log("✅ 代币符号:", await kjmToken.symbol());
  console.log("✅ 小数位数:", await kjmToken.decimals());
  console.log("✅ 总供应量:", hre.ethers.formatEther(await kjmToken.totalSupply()), "KJM");
  console.log("✅ 部署者余额:", hre.ethers.formatEther(await kjmToken.balanceOf(deployer)), "KJM");
};

export default deployKJMToken;

// 标签设置为"kjm"
deployKJMToken.tags = ["ERC20kjm202330552411"];
